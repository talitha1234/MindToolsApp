package com.talithariddiford.mindtoolsapp.data
import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import com.talithariddiford.mindtoolsapp.data.Mood

class ActivityTypeConverters {

    @TypeConverter
    fun fromHelpfulnessMap(value: Map<Mood, Int>?): String? {
        return value?.let { Json.encodeToString(it) }
    }

    @TypeConverter
    fun toHelpfulnessMap(value: String?): Map<Mood, Int>? {
        return value?.let { Json.decodeFromString(it) }
    }

    // Add other converters if needed for other complex fields
}


