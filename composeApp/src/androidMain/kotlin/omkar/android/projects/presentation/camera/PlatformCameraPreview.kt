package omkar.android.projects.presentation.camera

import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.DisposableEffectScope
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import omkar.android.projects.shared.camera.CameraController
import kotlin.apply

@Composable
actual fun PlatformCameraPreview() {
    CameraPreview()
}

@Composable
fun CameraPreview() {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val previewView = remember {
        PreviewView(context).apply {
            scaleType = PreviewView.ScaleType.FILL_CENTER
        }
    }

    val cameraController = remember {
        CameraController(
            previewView = previewView,
            lifecycleOwner = lifecycleOwner,
            context = context
        )
    }

    LaunchedEffect(Unit) {
        cameraController.startCamera()
    }

    DisposableEffect(Unit) {
        onDispose {
            cameraController.stopCamera()
        }
    }

    AndroidView(
        factory = {
            previewView
        },
        modifier = Modifier.fillMaxSize()
    )
}