package com.talithariddiford.mindtoolsapp.data

import com.talithariddiford.mindtoolsapp.util.getIconByName
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun convertHelpfulnessToString(map: Map<Mood, Int>): String {
    return Json.encodeToString(map)
}

fun convertStringToHelpfulness(str: String): Map<Mood, Int> {
    return Json.decodeFromString(str)
}

fun ActivityEntity.toActivity(): Activity =
    Activity(
        title = title,
        icon = getIconByName(iconName),
        iconName = iconName,
        id = id,
        mindToolResource = mindToolResource,
        helpfulnessByMood = convertStringToHelpfulness(helpfulnessByMood)
    )

fun Activity.toEntity(): ActivityEntity =
    ActivityEntity(
        id = id,
        title = title,
        iconName = iconName,
        mindToolResource = mindToolResource,
        helpfulnessByMood = convertHelpfulnessToString(helpfulnessByMood)
    )

