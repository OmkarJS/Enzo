package omkar.android.projects.app.di


import omkar.android.projects.data.repository.spatialpooler.SpatialPoolerImpl
import omkar.android.projects.data.repository.temporalmemory.TemporalMemoryImpl
import omkar.android.projects.domain.encoder.TouchEncoder
import omkar.android.projects.domain.encoder.VisualEncoder
import omkar.android.projects.domain.repository.encoders.ITouchEncoder
import omkar.android.projects.domain.repository.encoders.SensoryEncoder
import omkar.android.projects.domain.repository.spatialpooler.ISpatialPooler
import omkar.android.projects.domain.repository.temporalmemory.ITemporalMemory
import omkar.android.projects.domain.usecases.BitForgeUseCase
import omkar.android.projects.domain.usecases.CorticalLoopUseCase
import omkar.android.projects.presentation.bitforge.BitForgeViewmodel
import omkar.android.projects.presentation.home.HomeViewModel
import org.koin.dsl.module

/**
 - factory {
       Returns new viewmodel instance every time this is called. Add if needed.
   }

 - single - One instance
 */

val commonModule = module {
    // Repository
    single<SensoryEncoder> { VisualEncoder() }
    single<ISpatialPooler> { SpatialPoolerImpl() }
    single<ITemporalMemory> { TemporalMemoryImpl() }
    single<ITouchEncoder> { TouchEncoder() }

    // Usecase
    factory {
        CorticalLoopUseCase(
            get(),
            get(),
            get()
        )
    }

    factory {
        BitForgeUseCase(
            get()
        )
    }

    // Viewmodel
    factory { HomeViewModel(get()) }

    factory {
        BitForgeViewmodel(get())
    }
}