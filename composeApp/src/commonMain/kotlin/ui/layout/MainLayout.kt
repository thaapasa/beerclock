package fi.tuska.beerclock.ui.layout

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import fi.tuska.beerclock.localization.Strings
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainLayout(
    actionButton: @Composable () -> Unit = {},
    showTopBar: Boolean = true,
    snackbarHostState: SnackbarHostState? = null,
    content: @Composable (PaddingValues) -> Unit,
) {
    val strings = Strings.get()
    val coroutineScope = rememberCoroutineScope()
    val navigator = LocalNavigator.currentOrThrow
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                content = {
                    MainMenu(onNavigate = {
                        coroutineScope.launch {
                            drawerState.snapTo(DrawerValue.Closed)
                            navigator.push(it)
                        }
                    })
                }
            )
        },
        gesturesEnabled = true,
        content = {
            Scaffold(
                snackbarHost = { snackbarHostState?.let { SnackbarHost(it) } },
                topBar = {
                    if (showTopBar) {
                        TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        ), title = { Text(strings.appName) }, actions = {
                            ToggleDrawerButton(drawerState)
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
    )
}
