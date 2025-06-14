package com.talithariddiford.mindtoolsapp.model

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

data class Activity(
    @StringRes val titleRes: Int,
    val icon: ImageVector
)