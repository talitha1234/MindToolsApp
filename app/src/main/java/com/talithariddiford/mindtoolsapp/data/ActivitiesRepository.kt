package com.talithariddiford.mindtoolsapp.data

import kotlinx.coroutines.flow.Flow

interface ActivitiesRepository {

    fun getActivities(): Flow<List<Activity>>

    suspend fun addActivity(activity: Activity)

    suspend fun updateActivity(activity: Activity)

    suspend fun getActivityById(id: String): Activity?
}
