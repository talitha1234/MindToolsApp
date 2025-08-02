package com.talithariddiford.mindtoolsapp

import android.app.Application

import com.talithariddiford.mindtoolsapp.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ActivitiesApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(appModules)
        }
    }
}
