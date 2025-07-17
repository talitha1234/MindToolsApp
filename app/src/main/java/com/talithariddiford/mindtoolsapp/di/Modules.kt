import com.talithariddiford.mindtoolsapp.data.ActivitiesRepository
import com.talithariddiford.mindtoolsapp.data.ActivitiesRepositoryImpl
import com.talithariddiford.mindtoolsapp.viewmodel.ActivitiesViewModel
import com.talithariddiford.mindtoolsapp.viewmodel.LinkVideoCreationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel // make sure this is imported
import org.koin.dsl.module

val appModules = module {
    single<ActivitiesRepository> { ActivitiesRepositoryImpl() }
    viewModel { ActivitiesViewModel(get()) }
    viewModel { LinkVideoCreationViewModel() }
}
