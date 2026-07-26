package omkar.android.projects.app.utils

import androidx.navigation.NavController
import omkar.android.projects.app.constants.Constants

fun NavController.navigateToHomeScreen() {
    this.navigate(Constants.Routes.HOME)
}

fun NavController.navigateToProfileScreen() {
    this.navigate(Constants.Routes.PROFILE)
}

fun NavController.navigateToVisualScreen() {
    this.navigate(Constants.Routes.VISUAL_SCREEN)
}

fun NavController.navigateToBitForgeScreen() {
    this.navigate(Constants.Routes.BIT_FORGE_SCREEN)
}