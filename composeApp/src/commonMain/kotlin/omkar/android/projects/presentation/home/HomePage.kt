package omkar.android.projects.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import omkar.android.projects.app.theme.LocalAppColors
import omkar.android.projects.app.widget.icon.CustomIcon
import org.koin.compose.koinInject
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun HomePage(
    onProfileClicked: () -> Unit
) {
    val colors = LocalAppColors.current
    val homeViewModel: HomeViewModel = koinInject()

    Scaffold(
        topBar = {

        },
        floatingActionButton = {
            Column {
                FloatingActionButton(
                    onClick = {

                    },
                    contentColor = colors.primary
                ) {
                    CustomIcon(icon = Icons.Outlined.Favorite, iconColor = Color.White)
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

        }
    }
}

