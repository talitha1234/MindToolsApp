package com.talithariddiford.mindtoolsapp.data

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

data class Activity(
    @StringRes val titleRes: Int,
    val icon: ImageVector,
    val mindToolResource: String,      // e.g. pdf, phone-number, or YouTube-URL
    val helpfulnessByMood: MutableMap<Mood, Int> = mutableMapOf()
)
