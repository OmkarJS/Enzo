package omkar.android.projects.app.di

import android.content.Context
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import omkar.android.projects.data.source.AndroidVisualInputSource
import omkar.android.projects.data.source.pose.AndroidPoseSourceImpl
import omkar.android.projects.domain.repository.inputsource.VisualInputSource
import omkar.android.projects.domain.repository.pose.IPoseSource
import org.koin.dsl.module

val androidModule = module {
    single<IPoseSource> { AndroidPoseSourceImpl(get()) }

    factory<VisualInputSource> { (lifecycleOwner: LifecycleOwner, previewView: PreviewView) ->
        AndroidVisualInputSource(get(), lifecycleOwner, previewView, get())
    }
}