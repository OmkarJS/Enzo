package omkar.android.projects.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import omkar.android.projects.app.components.LargeText
import omkar.android.projects.app.components.SmallSpacer
import omkar.android.projects.app.components.TextConfig
import omkar.android.projects.app.theme.LocalAppColors
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun HomePage(
    onProfileClicked: () -> Unit,
    onCameraClicked: () -> Unit,
    onBitForgeClick: () -> Unit
) {
    val colors = LocalAppColors.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HomeItem(
            "Brightness demo",
            onClick = {
                onCameraClicked()
            }
        )

        SmallSpacer()

        HomeItem(
            "BitForge",
            onClick = {
                onBitForgeClick()
            }
        )
    }
}

@Composable
fun HomeItem(
    title: String,
    onClick: () -> Unit
) {
    val colors = LocalAppColors.current

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(colors.primaryVariant)
            .clickable{
                onClick()
            }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.Center
    ) {
        LargeText(
            title,
            config = TextConfig(
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        )
    }
}

