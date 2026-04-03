package omkar.android.projects.app.di


import omkar.android.projects.app.expectuals.HttpClientEngine
import omkar.android.projects.data.remote.ExampleClient
import omkar.android.projects.data.repository.ExampleRepositoryImpl
import omkar.android.projects.domain.encoder.VisualEncoder
import omkar.android.projects.domain.repository.ExampleRepository
import omkar.android.projects.domain.repository.SensoryEncoder
import omkar.android.projects.domain.usecases.CorticalLoopUseCase
import omkar.android.projects.presentation.home.HomeViewModel
import org.koin.dsl.module

val commonModule = module {
    // Repository
    single<ExampleRepository> { ExampleRepositoryImpl(get()) }
    single<SensoryEncoder> { VisualEncoder() }

    // Usecase
    factory { CorticalLoopUseCase(get()) }

    // Client
    val httpClient = HttpClientEngine().create()
    single { ExampleClient(httpClient = httpClient) }

    // Viewmodel
    factory { HomeViewModel(get()) }

    factory {
        // Returns new viewmodel instance every time this is called. Add if needed.
    }
}