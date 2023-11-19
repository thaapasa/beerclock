package fi.tuska.beerclock

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import cafe.adriel.voyager.navigator.Navigator
import fi.tuska.beerclock.database.DatabaseProvider
import fi.tuska.beerclock.database.LocalDatabase
import fi.tuska.beerclock.screens.HomeScreen
import fi.tuska.beerclock.ui.theme.AppTheme

@Composable
fun App() {
    AppTheme {
        CompositionLocalProvider(LocalDatabase provides DatabaseProvider.database) {
            Navigator(
                screen = HomeScreen
            )
        }
    }
}
