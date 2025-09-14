package omkar.android.projects.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import omkar.android.projects.app.theme.LocalAppColors
import org.koin.compose.koinInject

@Composable
fun HomePage() {
    val navigator = LocalNavigator.currentOrThrow
    val colors = LocalAppColors.current
    val homeViewModel: HomeViewModel = koinInject()

    // Search
    var searchText by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }

    fun hideSearchSuggestion() {
        searchText = ""
        isSearching = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.primary),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

    }
}

