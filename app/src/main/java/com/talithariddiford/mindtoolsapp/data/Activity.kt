// Activity.kt
package com.talithariddiford.mindtoolsapp.data

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

data class Activity(
    val id: String, // <-- This makes mapping easy!
    @StringRes val titleRes: Int,
    val icon: ImageVector,
    val iconName: String, // for round-tripping to DB
    val mindToolResource: String,
    val helpfulnessByMood: Map<Mood, Int>
)
