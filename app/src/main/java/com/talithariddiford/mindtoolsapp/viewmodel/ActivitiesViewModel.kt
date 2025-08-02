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

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import android.os.CountDownTimer
import com.talithariddiford.mindtoolsapp.viewmodel.ActivitiesUiState

class ActivitiesViewModel(
    private val activityRepository: ActivitiesRepository
) : ViewModel() {

    // Filter moods backing field and public getter
    private var _filterMoods: Set<Mood> = emptySet()
    val filterMoods: Set<Mood>
        get() = _filterMoods

    fun setFilterMoods(selected: Set<Mood>) {
        _filterMoods = selected
        // trigger UI update or reload here if necessary
    }

    private val _uiState = MutableStateFlow(ActivitiesUiState())
    val uiState: StateFlow<ActivitiesUiState> = _uiState.asStateFlow()

    var selectedMoods by mutableStateOf<Set<Mood>>(emptySet())

    var showMoodDialog = mutableStateOf(false)

    // --- Feedback prompt related ---
    private var currentOpenedActivityId: String? = null

    private val _showFeedbackPrompt = MutableStateFlow<Pair<String?, Boolean>?>(null)
    val showFeedbackPrompt: StateFlow<Pair<String?, Boolean>?> = _showFeedbackPrompt

    private var currentSelectedMoods: Set<Mood> = emptySet()

    private var feedbackTimer: CountDownTimer? = null

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
        feedbackTimer = object : CountDownTimer(30 * 1000, 1000) {
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


    fun updateFilterMoods(selected: Set<Mood>) {
        setFilterMoods(selected)

    }
}

enum class FeedbackResponse {
    YES,
    NO,
    NO_CHANGE
}
