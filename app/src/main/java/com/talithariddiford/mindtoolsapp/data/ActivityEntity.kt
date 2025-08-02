package com.talithariddiford.mindtoolsapp.data

import androidx.annotation.StringRes
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "activity")
data class ActivityEntity(
    @PrimaryKey val id: String,
    @StringRes val titleRes: Int,
    val iconName: String, // String for icon
    val mindToolResource: String,
    val helpfulnessByMood: String = "{}", // Save as JSON string, needs converter
    val createdAt: String = "",
    val updatedAt: String = "",
)
