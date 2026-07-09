package omkar.android.projects.presentation.camera

import androidx.compose.runtime.Composable
import omkar.android.projects.domain.repository.inputsource.VisualInputSource

@Composable
expect fun PlatformCameraPreview(onStartCamera: (VisualInputSource) -> Unit)