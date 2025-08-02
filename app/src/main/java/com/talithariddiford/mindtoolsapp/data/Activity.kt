// Activity.kt
package com.talithariddiford.mindtoolsapp.data


import androidx.compose.ui.graphics.vector.ImageVector

data class Activity(
    val id: String, // <-- This makes mapping easy!
    val title: String,
    val icon: ImageVector,
    val iconName: String, // for round-tripping to DB
    val mindToolResource: String,
    val helpfulnessByMood: Map<Mood, Int>
)
