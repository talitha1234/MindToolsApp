package com.talithariddiford.mindtoolsapp.viewmodel

import com.talithariddiford.mindtoolsapp.data.Activity
import com.talithariddiford.mindtoolsapp.data.Mood

data class ActivitiesUiState(
    val activities: List<Activity> = emptyList(),
    val selectedMood: Mood? = null,
    val isMoodDialogVisible: Boolean = false
)
