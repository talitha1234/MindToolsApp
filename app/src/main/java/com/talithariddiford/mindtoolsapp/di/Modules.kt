package com.talithariddiford.mindtoolsapp.di


import com.talithariddiford.mindtoolsapp.data.ActivitiesRepository
import com.talithariddiford.mindtoolsapp.data.ActivitiesRepositoryImpl
import com.talithariddiford.mindtoolsapp.viewmodel.ActivitiesViewModel

import org.koin.dsl.module

val appModules = module {
    single<ActivitiesRepository> { ActivitiesRepositoryImpl() }
    single { ActivitiesViewModel(get()) }
}