package omkar.android.projects.app.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import omkar.android.projects.app.constants.Constants.Routes
import omkar.android.projects.app.theme.LocalAppColors
import omkar.android.projects.app.utils.navigateToBitForgeScreen
import omkar.android.projects.app.utils.navigateToProfileScreen
import omkar.android.projects.app.utils.navigateToVisualScreen
import omkar.android.projects.presentation.bitforge.BitForgePage
import omkar.android.projects.presentation.home.HomePage
import omkar.android.projects.presentation.profile.ProfilePage
import omkar.android.projects.presentation.visuals.VisualScreen

@Composable
fun MyAppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.HOME,
        modifier = Modifier
            .fillMaxSize()
            .background(LocalAppColors.current.background)
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        composable(Routes.HOME) {
            HomePage(
                onProfileClicked = {
                    navController.navigateToProfileScreen()
                },
                onCameraClicked = {
                    navController.navigateToVisualScreen()
                },
                onBitForgeClick = {
                    navController.navigateToBitForgeScreen()
                }
            )
        }

        composable(Routes.PROFILE) {
            ProfilePage(
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }

        composable(Routes.VISUAL_SCREEN) {
            VisualScreen(
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }

        composable(Routes.BIT_FORGE_SCREEN) {
            BitForgePage (
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }
    }
}