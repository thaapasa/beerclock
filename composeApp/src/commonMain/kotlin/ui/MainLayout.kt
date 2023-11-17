package fi.tuska.beerclock.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import fi.tuska.beerclock.localization.strings
import kotlinx.coroutines.launch

@Composable
fun MainLayout(
    content: @Composable (PaddingValues) -> Unit,
    actionButton: @Composable () -> Unit = {}
) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val navigator = LocalNavigator.currentOrThrow
    Scaffold(scaffoldState = scaffoldState, topBar = {
        TopAppBar(title = { Text(strings.appName) }, actions = {
            ToggleDrawerButton(scaffoldState.drawerState)
        })
    }, drawerContent = {
        MainMenu(selectScreen = {
            coroutineScope.launch {
                scaffoldState.drawerState.close()
                navigator.push(it)
            }
        })
    }, floatingActionButton = actionButton,
        drawerGesturesEnabled = true, content = content
    )

}
