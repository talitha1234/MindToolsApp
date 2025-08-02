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

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ActivitiesViewModel(
    private val activityRepository: ActivitiesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ActivitiesUiState())
    val uiState: StateFlow<ActivitiesUiState> = _uiState.asStateFlow()

    var selectedMoods by mutableStateOf<Set<Mood>>(emptySet())

    var showMoodDialog = mutableStateOf(false)

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
        viewModelScope.launch {
            activityRepository.addActivity(activity)
            loadActivities()
        }
    }





}

