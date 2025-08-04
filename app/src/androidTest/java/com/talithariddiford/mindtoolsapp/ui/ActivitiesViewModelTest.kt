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

    @Test
    fun testLoadActivities() = runBlocking {
        val activity = Activity(
            id = "load-test",
            title = "Test Load",
            icon = Icons.Rounded.Call,
            iconName = "call",
            mindToolResource = "tel:+123456789",
            helpfulnessByMood = Mood.entries.associateWith { 3 }
        )
        fakeRepo.addActivity(activity)

        viewModel.loadActivities()

        val loadedActivities = viewModel.uiState.first { it.activities.isNotEmpty() }.activities
        assertEquals(1, loadedActivities.size)
        assertEquals(activity.id, loadedActivities[0].id)
    }
//    @Test
//    fun testOnFeedbackResponseUpdatesHelpfulness() = runBlocking {
//        val activity = Activity(
//            id = "feedback-test",
//            title = "Feedback Test",
//            icon = Icons.Rounded.Call,
//            iconName = "call",
//            mindToolResource = "tel:+123456789",
//            helpfulnessByMood = Mood.entries.associateWith { 4 }
//        )
//        fakeRepo.addActivity(activity)
//        viewModel.loadActivities()
//
//        viewModel.setCurrentSelectedMoods(setOf(Mood.SAD))
//
//        viewModel.onFeedbackResponse("feedback-test", FeedbackResponse.YES)
//
//        // Wait a moment for the update & reload
//        kotlinx.coroutines.delay(100)
//
//        val updatedActivity = viewModel.uiState.first { it.activities.any { it.id == "feedback-test" } }
//            .activities.find { it.id == "feedback-test" }!!
//
//        val updatedScore = updatedActivity.helpfulnessByMood[Mood.SAD]
//        assertEquals(5, updatedScore)
//    }
//
//    @Test
//    fun testFetchRandomQuoteUpdatesQuoteAndEmitsEvent() = runBlocking {
//        val testQuote = "Test inspirational quote — Author"
//
//        val testViewModel = object : ActivitiesViewModel(fakeRepo) {
//            override suspend fun fetchQuoteFromApi(): String {
//                return testQuote
//            }
//        }
//
//        var emittedQuote: String? = null
//        val job = launch {
//            testViewModel.snackbarEvent.collectLatest {
//                emittedQuote = it
//            }
//        }
//
//        testViewModel.fetchRandomQuote()
//
//        kotlinx.coroutines.delay(100) // wait for the flow emission
//
//        assertEquals(testQuote, testViewModel.currentQuote.value)
//        assertEquals(testQuote, emittedQuote)
//
//        job.cancel()
//    }




}
