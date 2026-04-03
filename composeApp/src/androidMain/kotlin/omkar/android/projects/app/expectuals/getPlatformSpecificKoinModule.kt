package omkar.android.projects.app.expectuals

import omkar.android.projects.app.di.androidModule
import org.koin.core.module.Module

actual fun getPlatformSpecificKoinModule(): Module {
    return androidModule
}