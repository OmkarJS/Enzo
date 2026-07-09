package omkar.android.projects.presentation.visuals

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import omkar.android.projects.app.theme.LocalAppColors
import omkar.android.projects.app.widget.icon.CustomIcon
import omkar.android.projects.presentation.camera.PlatformCameraPreview
import omkar.android.projects.presentation.home.HomeViewModel
import org.koin.compose.koinInject

@Composable
fun VisualScreen(
    onBackPressed: () -> Unit
) {
    val homeViewModel: HomeViewModel = koinInject()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        PlatformCameraPreview(
            onStartCamera = { source ->
                homeViewModel.startCorticalLoop(source)
            }
        )

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

        HtmMonitor(homeViewModel)
    }
}

@Composable
fun HtmMonitor(viewModel: HomeViewModel) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .background(LocalAppColors.current.black)
    ) {
        Text("Reality (Actual): ${state?.actualBrightness ?: 0}")
        Text("Memory (Predicted): ${state?.predictedBrightness ?: 0}")

        LinearProgressIndicator(
            progress = (state?.confidence ?: 0) / 100f,
            modifier = Modifier.fillMaxWidth()
        )
        Text("Confidence: ${state?.confidence}%")
    }
}
