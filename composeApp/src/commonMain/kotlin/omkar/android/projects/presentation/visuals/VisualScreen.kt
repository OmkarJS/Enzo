package omkar.android.projects.presentation.visuals

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import omkar.android.projects.app.widget.icon.CustomIcon
import omkar.android.projects.presentation.camera.PlatformCameraPreview

@Composable
fun VisualScreen(
    onBackPressed: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        PlatformCameraPreview()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(10.dp)
                .align(Alignment.TopStart)
        ) {
            CustomIcon(
                icon = Icons.Default.ArrowBack,
                onClick = onBackPressed
            )
        }
    }
}
