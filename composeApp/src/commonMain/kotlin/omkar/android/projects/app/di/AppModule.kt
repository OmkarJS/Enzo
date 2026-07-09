package omkar.android.projects.app.di


import omkar.android.projects.data.repository.spatialpooler.SpatialPoolerImpl
import omkar.android.projects.data.repository.temporalmemory.TemporalMemoryImpl
import omkar.android.projects.domain.encoder.VisualEncoder
import omkar.android.projects.domain.repository.encoders.SensoryEncoder
import omkar.android.projects.domain.repository.spatialpooler.ISpatialPooler
import omkar.android.projects.domain.repository.temporalmemory.ITemporalMemory
import omkar.android.projects.domain.usecases.CorticalLoopUseCase
import omkar.android.projects.presentation.home.HomeViewModel
import org.koin.dsl.module

val commonModule = module {
    // Repository
    single<SensoryEncoder> { VisualEncoder() }
    single<ISpatialPooler> { SpatialPoolerImpl() }
    single<ITemporalMemory> { TemporalMemoryImpl() }

    // Usecase
    factory {
        CorticalLoopUseCase(
            get(),
            get(),
            get()
        )
    }

    // Viewmodel
    factory { HomeViewModel(get()) }

    factory {
        // Returns new viewmodel instance every time this is called. Add if needed.
    }
}