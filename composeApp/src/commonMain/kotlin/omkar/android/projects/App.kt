package omkar.android.projects

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*
import omkar.android.projects.app.theme.AppTheme
import omkar.android.projects.app.navigation.MyAppNavigation
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    AppTheme(darkTheme = isSystemInDarkTheme()) {
        MyAppNavigation()
    }
}