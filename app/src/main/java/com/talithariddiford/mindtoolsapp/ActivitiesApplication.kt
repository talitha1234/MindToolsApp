package com.talithariddiford.mindtoolsapp

import android.app.Application
import appModules
import org.koin.core.context.startKoin

class ActivitiesApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModules)
        }
    }
}
