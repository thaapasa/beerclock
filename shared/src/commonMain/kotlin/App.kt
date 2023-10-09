package fi.tuska.beerclock.common

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import fi.tuska.beerclock.common.screens.HomeScreen

@Composable
fun App() {
    Navigator(
        screen = HomeScreen
    )
}
