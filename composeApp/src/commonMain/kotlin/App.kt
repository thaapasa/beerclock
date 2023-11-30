package fi.tuska.beerclock

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import fi.tuska.beerclock.screens.today.HomeScreen
import fi.tuska.beerclock.ui.theme.AppTheme

@Composable
fun App() {
    AppTheme {
        Navigator(
            screen = HomeScreen
        )
    }
}
