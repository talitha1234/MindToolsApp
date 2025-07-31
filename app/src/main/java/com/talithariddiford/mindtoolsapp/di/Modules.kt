import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.talithariddiford.mindtoolsapp.data.ActivitiesRepository
import com.talithariddiford.mindtoolsapp.data.ActivitiesRepositoryImpl
import com.talithariddiford.mindtoolsapp.viewmodel.ActivitiesViewModel
import com.talithariddiford.mindtoolsapp.viewmodel.LinkVideoCreationViewModel
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.androidx.viewmodel.dsl.viewModel // make sure this is imported
import org.koin.dsl.module
import retrofit2.Retrofit

val appModules = module {
    single<ActivitiesRepository> { ActivitiesRepositoryImpl() }
    viewModel { ActivitiesViewModel(get()) }
    viewModel { LinkVideoCreationViewModel() }
    single {
        val json = Json { ignoreUnknownKeys = true }
        Retrofit.Builder()
            .addConverterFactory(
                json.asConverterFactory(contentType = "application/json".toMediaType())
            )
            .baseUrl("https://cataas.com/api/")
            .build()
    }

}
