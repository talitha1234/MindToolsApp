package com.talithariddiford.mindtoolsapp.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Attachment
import androidx.compose.material.icons.filled.OndemandVideo
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Tune    // <-- Add this line!
import androidx.compose.ui.graphics.vector.ImageVector


fun getIconByName(iconName: String): ImageVector {
    return when (iconName) {
        "tune" -> Icons.Filled.Tune
        "attachment" -> Icons.Filled.Attachment
        "ondemand_video" -> Icons.Filled.OndemandVideo
        "call" -> Icons.Filled.Call
        // ...add others as needed...
        else -> Icons.Filled.Help
    }
}
