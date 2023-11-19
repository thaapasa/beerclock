package fi.tuska.beerclock

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import cafe.adriel.voyager.navigator.Navigator
import fi.tuska.beerclock.database.DatabaseProvider
import fi.tuska.beerclock.database.LocalDatabase
import fi.tuska.beerclock.screens.HomeScreen

@Composable
fun App() {
    MaterialTheme {
        CompositionLocalProvider(LocalDatabase provides DatabaseProvider.database) {
            Navigator(
                screen = HomeScreen
            )
        }
    }
}
