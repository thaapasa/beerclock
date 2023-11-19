package fi.tuska.beerclock.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import fi.tuska.beerclock.localization.strings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainLayout(
    content: @Composable (PaddingValues) -> Unit,
    actionButton: @Composable () -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    val navigator = LocalNavigator.currentOrThrow
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(strings.appName) }, actions = {
                // ToggleDrawerButton(scaffoldState.drawerState)
            })
        },
        /*drawerContent = {
        MainMenu(selectScreen = {
            coroutineScope.launch {
                // scaffoldState.drawerState.close()
                navigator.push(it)
            }
        })

    }, */
        floatingActionButton = actionButton,
        // drawerGesturesEnabled = true,
        content = content
    )

}
