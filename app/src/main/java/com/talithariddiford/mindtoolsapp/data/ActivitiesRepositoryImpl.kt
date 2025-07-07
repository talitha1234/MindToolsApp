package com.talithariddiford.mindtoolsapp.data
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Attachment
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.OndemandVideo
import com.talithariddiford.mindtoolsapp.data.Activity
import com.talithariddiford.mindtoolsapp.R

class ActivitiesRepositoryImpl(): ActivitiesRepository {
    override fun loadActivities(): List<Activity> {
        return listOf<Activity>(
            Activity(R.string.cbt, Icons.Rounded.Attachment),
            Activity(R.string.phone_call, Icons.Rounded.Call),
            Activity(R.string.sleep_video, Icons.Rounded.OndemandVideo),
            Activity(R.string.cbt, Icons.Rounded.Attachment),
            Activity(R.string.call_lifeline, Icons.Rounded.Call),
            Activity(R.string.sleep_video, Icons.Rounded.OndemandVideo),
            Activity(R.string.cbt, Icons.Rounded.Attachment),
            Activity(R.string.call_lifeline, Icons.Rounded.Call),
            Activity(R.string.sleep_video, Icons.Rounded.OndemandVideo),
        )

    }

}
