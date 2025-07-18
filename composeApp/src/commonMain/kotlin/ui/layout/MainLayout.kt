package fi.tuska.beerclock.ui.layout

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import fi.tuska.beerclock.localization.Strings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainLayout(
    actionButton: @Composable () -> Unit = {},
    showTopBar: Boolean = true,
    snackbarHostState: SnackbarHostState? = null,
    content: @Composable (PaddingValues) -> Unit,
) {
    val strings = Strings.get()
    val navigator = LocalNavigator.currentOrThrow
    Scaffold(
        snackbarHost = { snackbarHostState?.let { SnackbarHost(it) } },
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = {
            if (showTopBar) {
                TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ), title = { Text(strings.appName) }, actions = {
                    MainMenuButton()
                })
            }
        },
        floatingActionButton = actionButton,
        content = content,
        bottomBar = {
            BottomNavigationBar(current = navigator.lastItem, onNavigate = {
                navigator.replaceAll(it)
            })
        }
    )
}
