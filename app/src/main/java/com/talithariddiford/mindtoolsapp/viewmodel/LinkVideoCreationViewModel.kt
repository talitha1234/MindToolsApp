package com.talithariddiford.mindtoolsapp.viewmodel

import androidx.lifecycle.ViewModel

open class LinkVideoCreationViewModel : ViewModel() {
    open fun isValidURL(url: String): Boolean {
        val urlRegex = Regex(
            pattern = """^(https?://)?(www\.)?[a-zA-Z0-9\-]+\.[a-zA-Z]{2,}.*$""",
            options = setOf(RegexOption.IGNORE_CASE)
        )
        return url.matches(urlRegex)
    }


}