package com.talithariddiford.mindtoolsapp.ui

import com.talithariddiford.mindtoolsapp.data.ActivitiesRepository
import com.talithariddiford.mindtoolsapp.data.Activity
import com.talithariddiford.mindtoolsapp.data.Mood
import com.talithariddiford.mindtoolsapp.viewmodel.ActivitiesViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Call
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Assert.assertEquals


// Fake repository implementing your interface
class FakeActivitiesRepository : ActivitiesRepository {
    private val activities = mutableListOf<Activity>()
    private val activitiesFlow = MutableStateFlow<List<Activity>>(emptyList())

    override fun getActivities(): Flow<List<Activity>> = activitiesFlow.asStateFlow()

    override suspend fun addActivity(activity: Activity) {
        activities.add(activity)
        activitiesFlow.value = activities.toList() // Update flow with new list
    }

    override suspend fun updateActivity(activity: Activity) {
        val index = activities.indexOfFirst { it.id == activity.id }
        if (index != -1) {
            activities[index] = activity
            activitiesFlow.value = activities.toList()
        }
    }

    override suspend fun getActivityById(id: String): Activity? {
        return activities.find { it.id == id }
    }
}

class ActivitiesViewModelTest {
    private val fakeRepo = FakeActivitiesRepository()
    private val viewModel = ActivitiesViewModel(fakeRepo)

    @Test
    fun testAddActivity() = runBlocking {
        val activity = Activity(
            id = "test-id-123",
            title = "Call Lifeline",
            icon = Icons.Rounded.Call,
            iconName = "call",
            mindToolResource = "tel:+61131114",
            helpfulnessByMood = Mood.entries.associateWith { 3 }
        )

        viewModel.addActivity(activity)

        val loadedActivities = viewModel.uiState.first { it.activities.isNotEmpty() }.activities

        assertEquals(1, loadedActivities.size)
        assertEquals(activity.mindToolResource, loadedActivities[0].mindToolResource)
    }

}
