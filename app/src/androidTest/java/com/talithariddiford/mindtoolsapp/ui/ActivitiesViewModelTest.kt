package com.talithariddiford.mindtoolsapp.ui
import com.talithariddiford.mindtoolsapp.data.ActivitiesRepository
import com.talithariddiford.mindtoolsapp.data.Activity
import com.talithariddiford.mindtoolsapp.viewmodel.ActivitiesViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Call
import com.talithariddiford.mindtoolsapp.R
import org.junit.Test
import org.junit.Assert.assertEquals


class FakeActivitiesRepository : ActivitiesRepository {
    private val activities = mutableListOf<Activity>()
    override fun loadActivities(): List<Activity> = activities

    override fun addActivity(activity: Activity) {
        activities.add(activity)
    }
}

class ActivitiesViewModelTest {
    private val fakeRepo = FakeActivitiesRepository()
    private val viewModel = ActivitiesViewModel(fakeRepo)

    @Test
    fun testAddActivity() {
        val activity = Activity(
            titleRes = R.string.call_lifeline,
            icon = Icons.Rounded.Call,
            mindToolResource = "tel:+61131114"
        )

        viewModel.addActivity(activity)
        viewModel.loadActivities()

        val loadedActivities = viewModel.uiState.value.activities

        assertEquals(1, loadedActivities.size)
        assertEquals(activity.mindToolResource, loadedActivities[0].mindToolResource)
    }



}
