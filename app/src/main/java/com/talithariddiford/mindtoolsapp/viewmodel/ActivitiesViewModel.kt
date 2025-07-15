package com.talithariddiford.mindtoolsapp.viewmodel

import androidx.lifecycle.ViewModel

import com.talithariddiford.mindtoolsapp.data.ActivitiesRepository

class ActivitiesViewModel(
    private val activityRepository: ActivitiesRepository): ViewModel() {

    fun loadActivities() = activityRepository.loadActivities()
}