package fi.tuska.beerclock.ui.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.screens.about.AboutScreen
import fi.tuska.beerclock.screens.library.DrinkLibraryScreen
import fi.tuska.beerclock.screens.settings.SettingsScreen

@Composable
fun MainMenuContent(onNavigate: (select: Screen) -> Unit) {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {
        DropdownMenuItem(
            onClick = { onNavigate(SettingsScreen) },
            text = { Text(Strings.get().menu.settings) }
        )
        DropdownMenuItem(
            onClick = { onNavigate(DrinkLibraryScreen) },
            text = { Text(Strings.get().menu.drinkLibrary) }
        )
        DropdownMenuItem(
            onClick = { onNavigate(AboutScreen) },
            text = { Text(Strings.get().menu.about) }
        )
    }
}

@Composable
fun MainMenuButton(icon: ImageVector = Icons.Default.Menu) {
    val strings = Strings.get()
    val navigator = LocalNavigator.currentOrThrow
    var expanded by remember { mutableStateOf(false) }
    Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = icon,
                contentDescription = strings.menu.menu,
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            MainMenuContent(onNavigate = { navigator.push(it) })
        }
    }
}
