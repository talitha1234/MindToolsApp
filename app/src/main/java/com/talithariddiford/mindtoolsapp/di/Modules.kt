package com.talithariddiford.mindtoolsapp.di

import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.talithariddiford.mindtoolsapp.data.*
import com.talithariddiford.mindtoolsapp.viewmodel.ActivitiesViewModel
import com.talithariddiford.mindtoolsapp.viewmodel.LinkVideoCreationViewModel
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            ActivityDatabase::class.java,
            "activity-database"
        ).build()
    }
    single { get<ActivityDatabase>().activityDao() }
}

val networkModule = module {
    single {
        val json = Json { ignoreUnknownKeys = true }
        Retrofit.Builder()
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
            )
            .baseUrl("https://cataas.com/api/")
            .build()
    }
}

val repositoryModule = module {
    single<ActivitiesRepository> { ActivitiesRepositoryImpl(get()) }
}

val viewModelModule = module {
    viewModel { ActivitiesViewModel(get()) }
    viewModel { LinkVideoCreationViewModel() }
}

// Combine all modules to load in your application
val appModules = listOf(
    databaseModule,
    networkModule,
    repositoryModule,
    viewModelModule
)

