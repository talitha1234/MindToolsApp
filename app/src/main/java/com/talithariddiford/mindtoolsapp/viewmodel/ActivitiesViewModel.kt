package com.talithariddiford.mindtoolsapp.viewmodel

import androidx.lifecycle.viewmodel.compose.viewModel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel

import com.talithariddiford.mindtoolsapp.data.ActivitiesRepository
import com.talithariddiford.mindtoolsapp.data.ActivitiesRepositoryImpl
import com.talithariddiford.mindtoolsapp.data.Activity
import com.talithariddiford.mindtoolsapp.data.Mood
import com.talithariddiford.mindtoolsapp.ui.ActivityToolsPage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ActivitiesViewModel(
    private val activityRepository: ActivitiesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ActivitiesUiState())
    val uiState: StateFlow<ActivitiesUiState> = _uiState.asStateFlow()

    var selectedMood by mutableStateOf<Mood?>(null)
    var showMoodDialog = mutableStateOf(false)

    fun loadActivities() {
        val loaded = activityRepository.loadActivities()
        _uiState.value = _uiState.value.copy(activities = loaded)
    }

    fun onActivitySelected(activity: Activity, context: Context) {
        if (selectedMood == null) {
            showMoodDialog.value = true
            return
        }

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

    fun confirmMoodAndLaunch(activity: Activity, context: Context, mood: Mood) {
        selectedMood = mood
        showMoodDialog.value = false
        onActivitySelected(activity, context)
    }

    fun addActivity(activity: Activity) {
        activityRepository.addActivity(activity)
        _uiState.value = _uiState.value.copy(activities = activityRepository.loadActivities())
        Log.d("ActivitiesViewModel", "Activity added via ViewModel: $activity")
    }





}

