package com.talithariddiford.mindtoolsapp.data

import androidx.annotation.StringRes
import com.talithariddiford.mindtoolsapp.R

enum class Mood(@StringRes val label: Int) {
    SAD(R.string.mood_sad),
    ANXIOUS(R.string.mood_anxious),
    ANGRY(R.string.mood_angry),
    LONELY(R.string.mood_lonely),
    TIRED(R.string.mood_tired)
}
