package com.talithariddiford.mindtoolsapp.data

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Attachment
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.OndemandVideo
import com.talithariddiford.mindtoolsapp.R

class ActivitiesRepositoryImpl : ActivitiesRepository {

    private val activities = mutableListOf(
        Activity(
            titleRes = R.string.cbt,
            icon     = Icons.Rounded.Attachment,
            mindToolResource  = "file:///android_asset/guide.pdf"
        ),
        Activity(
            titleRes = R.string.sleep_video,
            icon     = Icons.Rounded.OndemandVideo,
            mindToolResource  = "https://www.youtube.com/watch?v=i7xGF8F28zo"
        ),
        Activity(
            titleRes = R.string.call_lifeline,
            icon     = Icons.Rounded.Call,
            mindToolResource  = "tel:+61131114"
        )
    )


    override fun loadActivities(): List<Activity> = activities

    // Add Activity
    override fun addActivity(activity: Activity) {
        activities.add(activity)
        Log.d("ActivitiesRepository", "Activities now: $activities")

    }


}
