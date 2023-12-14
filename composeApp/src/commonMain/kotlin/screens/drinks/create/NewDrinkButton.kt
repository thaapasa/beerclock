package fi.tuska.beerclock.screens.drinks.create

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.screens.newdrink.NewDrinkSearchScreen

@Composable
fun NewDrinkButton() {
    val strings = Strings.get()
    val navigator = LocalNavigator.currentOrThrow
    LargeFloatingActionButton(onClick = {
        navigator.push(NewDrinkSearchScreen)
    }) {
        Icon(
            painter = AppIcon.BEER.painter(),
            contentDescription = strings.drinkDialog.createTitle,
            modifier = Modifier.size(36.dp)
        )
    }
}
