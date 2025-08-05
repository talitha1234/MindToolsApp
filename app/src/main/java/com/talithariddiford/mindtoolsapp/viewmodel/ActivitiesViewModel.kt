package com.talithariddiford.mindtoolsapp.viewmodel


import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.talithariddiford.mindtoolsapp.data.ActivitiesRepository
import com.talithariddiford.mindtoolsapp.data.Activity
import com.talithariddiford.mindtoolsapp.data.Mood
import java.net.URL
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import android.os.CountDownTimer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.withContext
import org.json.JSONArray


class ActivitiesViewModel(
    private val activityRepository: ActivitiesRepository
) : ViewModel() {

    // Filter moods backing field and public getter
    private val _filterMoods = MutableStateFlow<Set<Mood>>(emptySet())
    val filterMoods: StateFlow<Set<Mood>> = _filterMoods.asStateFlow()

    fun setFilterMoods(moods: Set<Mood>) {
        Log.d("ActivitiesViewModel", "setFilterMoods called with: $moods")
        _filterMoods.value = moods
    }



    private val _snackbarEvent = MutableSharedFlow<String>()
    val snackbarEvent = _snackbarEvent.asSharedFlow()
    private val _uiState = MutableStateFlow(ActivitiesUiState())
    val uiState: StateFlow<ActivitiesUiState> = _uiState.asStateFlow()

    var selectedMoods by mutableStateOf<Set<Mood>>(emptySet())

    var showMoodDialog = mutableStateOf(false)


    private var currentOpenedActivityId: String? = null

    private val _showFeedbackPrompt = MutableStateFlow<Pair<String?, Boolean>?>(null)
    val showFeedbackPrompt: StateFlow<Pair<String?, Boolean>?> = _showFeedbackPrompt

    private var currentSelectedMoods: Set<Mood> = emptySet()

    private var feedbackTimer: CountDownTimer? = null
    private val _currentQuote = MutableStateFlow("")

//    // added this public function for test
//    fun setCurrentSelectedMoods(moods: Set<Mood>) {
//        currentSelectedMoods = moods
//    }
    fun loadActivities() {
        viewModelScope.launch {
            activityRepository.getActivities().collectLatest { activities ->
                _uiState.value = _uiState.value.copy(activities = activities)
            }
        }
    }

    fun onActivitySelected(activity: Activity, context: Context) {
        if (selectedMoods.isEmpty()) {
            showMoodDialog.value = true
            return
        }

        currentOpenedActivityId = activity.id
        currentSelectedMoods = selectedMoods

        // Cancel any existing timer
        feedbackTimer?.cancel()

        // Start timer to show feedback prompt (testing with 30 seconds)
        feedbackTimer = object : CountDownTimer(5 * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) { /* no-op */ }
            override fun onFinish() {
                _showFeedbackPrompt.value = currentOpenedActivityId to true
            }
        }.start()

        val uri = activity.mindToolResource.toUri()
        val intent = when {
            uri.scheme == "tel" ->
                Intent(Intent.ACTION_DIAL, uri)

            uri.scheme?.startsWith("http") == true ->
                Intent(Intent.ACTION_VIEW, uri)

            uri.path?.endsWith(".pdf") == true -> {
                Toast.makeText(context, "PDF preview not available yet", Toast.LENGTH_SHORT).show()
                null
            }

            else -> null
        }

        intent?.let { context.startActivity(it) }
    }

    fun confirmMoodsAndLaunch(activity: Activity, context: Context, moods: Set<Mood>) {
        selectedMoods = moods
        showMoodDialog.value = false
        onActivitySelected(activity, context)
    }

    fun addActivity(activity: Activity) {
        Log.d("ActivitiesViewModel", "Adding activity: $activity")
        viewModelScope.launch {
            activityRepository.addActivity(activity)
            loadActivities()
        }
    }

    fun fetchRandomQuote() {

        viewModelScope.launch {
            val quote = fetchQuoteFromApi()
            _currentQuote.value = quote
            _snackbarEvent.emit(quote)
            Log.d("ActivitiesViewModel", "Emitting quote: $quote")
            _snackbarEvent.emit(quote)

        }
    }

    fun deleteActivity(activity: Activity) {
        viewModelScope.launch {
            activityRepository.deleteActivity(activity)
            loadActivities() // refresh list after delete
        }
    }


    private suspend fun fetchQuoteFromApi(): String = withContext(Dispatchers.IO) {
        try {
            val response = URL("https://zenquotes.io/api/random").readText()
            val jsonArray = JSONArray(response)
            val json = jsonArray.getJSONObject(0)
            val quote = json.getString("q")
            val author = json.getString("a")
            "$quote — $author"
        } catch (e: Exception) {
            "Failed to load quote"
        }
    }








    fun onFeedbackResponse(activityId: String, response: FeedbackResponse) {
        _showFeedbackPrompt.value = activityId to false

        val activity = _uiState.value.activities.find { it.id == activityId } ?: return

        val updatedHelpfulness = activity.helpfulnessByMood.toMutableMap()

        currentSelectedMoods.forEach { mood ->
            val currentScore = updatedHelpfulness[mood] ?: 3  // default starting score
            val newScore = when(response) {
                FeedbackResponse.YES -> (currentScore + 1).coerceAtMost(5)
                FeedbackResponse.NO -> (currentScore - 1).coerceAtLeast(0)
                FeedbackResponse.NO_CHANGE -> currentScore
            }
            updatedHelpfulness[mood] = newScore
        }


        val updatedActivity = activity.copy(helpfulnessByMood = updatedHelpfulness)


        viewModelScope.launch {
            activityRepository.updateActivity(updatedActivity)
            loadActivities()
        }
    }



}

enum class FeedbackResponse {
    YES,
    NO,
    NO_CHANGE
}
