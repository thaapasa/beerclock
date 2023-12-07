package fi.tuska.beerclock.screens.drinks.create

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.localization.Strings

@Composable
fun NewDrinkButton(onDrinksUpdated: () -> Unit) {
    val strings = Strings.get()
    var dialogOpen by remember { mutableStateOf(false) }
    LargeFloatingActionButton(onClick = {
        dialogOpen = true
    }) {
        Icon(
            painter = AppIcon.BEER.painter(),
            contentDescription = strings.newDrink.title,
            modifier = Modifier.size(36.dp)
        )
    }
    if (dialogOpen) {
        AddDrinkDialog(onDrinksUpdated = onDrinksUpdated) { dialogOpen = false }
    }
}
