package fi.tuska.beerclock.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import fi.tuska.beerclock.localization.strings
import fi.tuska.beerclock.screens.DrinksScreen
import fi.tuska.beerclock.screens.SettingsScreen
import fi.tuska.beerclock.screens.StatisticsScreen

@Composable
fun MainMenu(selectScreen: (select: Screen) -> Unit) {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {
        DropdownMenuItem(
            onClick = { selectScreen(SettingsScreen) },
            text = { Text(strings.menu.settings) }
        )
        DropdownMenuItem(
            onClick = { selectScreen(DrinksScreen) },
            text = { Text(strings.menu.drinks) }
        )
        DropdownMenuItem(
            onClick = { selectScreen(StatisticsScreen) },
            text = { Text(strings.menu.statistics) }
        )
    }
}