package fi.tuska.beerclock.ui.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import fi.tuska.beerclock.drinks.Category
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.screens.about.AboutScreen
import fi.tuska.beerclock.screens.disclosure.DisclosureScreen
import fi.tuska.beerclock.screens.library.DrinkLibraryScreen
import fi.tuska.beerclock.screens.mixcalculator.MixedDrinksScreen
import fi.tuska.beerclock.screens.settings.SettingsScreen


@Composable
fun ColumnScope.MainMenuContent(onNavigate: (select: Screen) -> Unit) {
    DropdownMenuItem(
        leadingIcon = { AppIcon.SETTINGS.icon() },
        onClick = { onNavigate(SettingsScreen) },
        text = { Text(Strings.get().menu.settings) }
    )
    DropdownMenuItem(
        leadingIcon = { AppIcon.DRINK.icon() },
        onClick = { onNavigate(DrinkLibraryScreen(Category.BEERS)) },
        text = { Text(Strings.get().menu.drinkLibrary) }
    )
    DropdownMenuItem(
        leadingIcon = { AppIcon.EXPERIMENT.icon() },
        onClick = { onNavigate(MixedDrinksScreen) },
        text = { Text(Strings.get().menu.mixedDrinkCalculator) }
    )
    HorizontalDivider(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp))
    DropdownMenuItem(
        leadingIcon = { AppIcon.INFO.icon() },
        onClick = { onNavigate(AboutScreen) },
        text = { Text(Strings.get().menu.about) }
    )
    DropdownMenuItem(
        leadingIcon = { AppIcon.CONTRACT.icon() },
        onClick = { onNavigate(DisclosureScreen) },
        text = { Text(Strings.get().menu.disclosure) }
    )
}

@Composable
fun MainMenuButton() {
    val strings = Strings.get()
    val navigator = LocalNavigator.currentOrThrow
    var expanded by remember { mutableStateOf(false) }
    Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                painter = AppIcon.MENU.painter(),
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
