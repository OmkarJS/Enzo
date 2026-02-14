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
import co.touchlab.kermit.Logger
import omkar.android.projects.app.constants.Constants
import omkar.android.projects.app.theme.LocalAppColors
import omkar.android.projects.app.utils.navigateToProfileScreen
import omkar.android.projects.presentation.home.HomePage
import omkar.android.projects.presentation.profile.ProfilePage

@Composable
fun MyAppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Constants.Routes.HOME,
        modifier = Modifier
            .fillMaxSize()
            .background(LocalAppColors.current.background)
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        composable(Constants.Routes.HOME) {
            HomePage(
                onProfileClicked = {
                    navController.navigateToProfileScreen()
                }
            )
        }

        composable(Constants.Routes.PROFILE) {
            ProfilePage(
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }
    }
}