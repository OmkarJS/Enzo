package omkar.android.projects.app.di

import android.content.Context
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import omkar.android.projects.data.source.CameraDataSource
import org.koin.dsl.module

val androidModule = module {
    factory { (context: Context, lifecycleOwner: LifecycleOwner, previewView: PreviewView) ->
        CameraDataSource(context, lifecycleOwner, previewView)
    }
}
