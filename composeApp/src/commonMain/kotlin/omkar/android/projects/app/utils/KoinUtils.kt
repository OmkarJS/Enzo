package omkar.android.projects.app.utils

import omkar.android.projects.app.di.commonModule
import omkar.android.projects.app.expectuals.getPlatformSpecificKoinModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

object KoinUtils {
    fun startKoinProcess(
        config: (KoinApplication.() -> Unit)? = null
    ) {
        startKoin {
            config?.invoke(this)
            modules(commonModule, getPlatformSpecificKoinModule())
        }
    }
}