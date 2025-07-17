package com.talithariddiford.mindtoolsapp.data

import com.talithariddiford.mindtoolsapp.data.Activity

interface ActivitiesRepository {
    fun loadActivities(): List<Activity>
    fun addActivity(activity: Activity)
}

