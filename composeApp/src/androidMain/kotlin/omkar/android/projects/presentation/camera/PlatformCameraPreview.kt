package omkar.android.projects.presentation.camera

import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import omkar.android.projects.data.source.CameraDataSource
import omkar.android.projects.domain.repository.VisualInputSource
import org.koin.core.parameter.parametersOf
import kotlin.apply
import org.koin.compose.koinInject

@Composable
actual fun PlatformCameraPreview(
    onStartCamera: (VisualInputSource) -> Unit
) {
    CameraPreview(onStartCamera)
}

@Composable
fun CameraPreview(
    onStartCamera: (VisualInputSource) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val previewView = remember {
        PreviewView(context).apply {
            scaleType = PreviewView.ScaleType.FILL_CENTER
        }
    }

    val cameraDataSource: CameraDataSource = koinInject<CameraDataSource> {
        parametersOf(context, lifecycleOwner, previewView)
    }

    LaunchedEffect(Unit) {
        cameraDataSource.startCamera()
        onStartCamera(cameraDataSource)
    }

    DisposableEffect(Unit) {
        onDispose {
            cameraDataSource.stopCamera()
        }
    }

    AndroidView(
        factory = {
            previewView
        },
        modifier = Modifier.fillMaxSize()
    )
}