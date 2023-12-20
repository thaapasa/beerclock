package fi.tuska.beerclock.ui.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.screens.library.DrinkLibraryScreen
import fi.tuska.beerclock.screens.settings.SettingsScreen

@Composable
fun MainMenu(onNavigate: (select: Screen) -> Unit) {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {
        DropdownMenuItem(
            onClick = { onNavigate(SettingsScreen) },
            text = { Text(Strings.get().menu.settings) }
        )
        DropdownMenuItem(
            onClick = { onNavigate(DrinkLibraryScreen) },
            text = { Text(Strings.get().menu.drinkLibrary) }
        )
    }
}
