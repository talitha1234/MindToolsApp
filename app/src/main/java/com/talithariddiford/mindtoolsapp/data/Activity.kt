package com.talithariddiford.mindtoolsapp.data

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

data class Activity(
    @StringRes val titleRes: Int,
    val icon: ImageVector,
    val payload: String      // e.g. pdf, phone-number, or YouTube-URL
)
