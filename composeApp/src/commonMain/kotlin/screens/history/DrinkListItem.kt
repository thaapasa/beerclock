package fi.tuska.beerclock.screens.history

import androidx.compose.foundation.clickable
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.drinks.DrinkRecordInfo
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.ui.components.UnitAvatar

@Composable
fun DrinkListItem(
    drink: DrinkRecordInfo,
    onModify: ((drink: DrinkRecordInfo) -> Unit)? = null,
    onDelete: ((drink: DrinkRecordInfo) -> Unit)? = null,
) {
    val strings = Strings.get()
    var selected by remember { mutableStateOf(false) }
    ListItem(
        overlineContent = { Text(strings.drink.drinkTime(drink.time)) },
        headlineContent = { Text(drink.name) },
        supportingContent = {
            Text(
                strings.drink.drinkSize(
                    quantityCl = drink.quantityCl,
                    abvPercentage = drink.abvPercentage
                )
            )
        },
        leadingContent = { drink.image.smallImage() },
        trailingContent = { UnitAvatar(units = drink.units()) },
        modifier = Modifier.clickable { selected = !selected },
        tonalElevation = 8.dp,
        shadowElevation = 16.dp
    )
    if (selected) {
        DrinkInfoDialog(
            drink,
            onClose = { selected = false },
            onDelete = onDelete,
            onModify = onModify
        )
    }
}
