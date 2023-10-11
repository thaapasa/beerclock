package fi.tuska.beerclock.common

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import cafe.adriel.voyager.navigator.Navigator
import fi.tuska.beerclock.common.database.DatabaseProvider
import fi.tuska.beerclock.common.database.LocalDatabase
import fi.tuska.beerclock.common.screens.HomeScreen

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
