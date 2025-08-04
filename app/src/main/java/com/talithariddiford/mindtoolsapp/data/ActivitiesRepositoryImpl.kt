package com.talithariddiford.mindtoolsapp.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class ActivitiesRepositoryImpl(
    private val dao: ActivityDao // Injected by Koin or manual constructor
) : ActivitiesRepository {

    override fun getActivities(): Flow<List<Activity>> =
        dao.getActivities().map { entities ->
            entities.map { it.toActivity() }
        }

    override suspend fun addActivity(activity: Activity) {
        withContext(Dispatchers.IO) {
            dao.insert(activity.toEntity())
        }
    }

    override suspend fun updateActivity(activity: Activity) {
        withContext(Dispatchers.IO) {
            dao.update(activity.toEntity())
        }
    }

    override suspend fun getActivityById(id: String): Activity? =
        dao.getActivityById(id)?.toActivity()

    override suspend fun deleteActivity(activity: Activity) {
        withContext(Dispatchers.IO) {
            dao.delete(activity.toEntity())
        }
    }

}

