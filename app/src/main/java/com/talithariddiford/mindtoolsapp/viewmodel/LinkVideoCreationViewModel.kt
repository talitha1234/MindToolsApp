package com.talithariddiford.mindtoolsapp.viewmodel

import java.net.URL

import androidx.lifecycle.ViewModel

open class LinkVideoCreationViewModel : ViewModel() {
    open fun isValidURL(url:String): Boolean {
        try {
            URL(url)
            return true
        } catch (e:Exception) {
            return false
        }
    }

}