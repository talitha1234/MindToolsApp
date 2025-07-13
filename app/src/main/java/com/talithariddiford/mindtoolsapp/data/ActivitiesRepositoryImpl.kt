package com.talithariddiford.mindtoolsapp.data
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Attachment
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.OndemandVideo
import com.talithariddiford.mindtoolsapp.data.Activity
import com.talithariddiford.mindtoolsapp.R

class ActivitiesRepositoryImpl(): ActivitiesRepository {
    override fun loadActivities(): List<Activity> {
        return listOf(
            Activity(
                titleRes = R.string.cbt,
                icon     = Icons.Rounded.Attachment,
                payload  = "file:///android_asset/guide.pdf"
            ),
            Activity(
                titleRes = R.string.sleep_video,
                icon     = Icons.Rounded.OndemandVideo,
                payload  = "https://www.youtube.com/watch?v=i7xGF8F28zo"

            ),
            Activity(
                titleRes = R.string.call_lifeline,
                icon     = Icons.Rounded.Call,
                payload  = "tel:+61131114"

            )
        )
    }

}
