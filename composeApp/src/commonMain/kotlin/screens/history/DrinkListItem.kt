package fi.tuska.beerclock.screens.history

import androidx.compose.foundation.clickable
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.drinks.DrinkRecordInfo
import fi.tuska.beerclock.localization.strings

@Composable
fun DrinkListItem(drink: DrinkRecordInfo, onClick: () -> Unit = { }) {
    ListItem(
        overlineContent = { Text(strings.drink.drinkTime(drink.time)) },
        headlineContent = { Text(drink.name) },
        supportingContent = {
            Text(
                strings.drink.itemDescription(
                    quantity = drink.quantityCl,
                    abv = drink.abvPercentage
                )
            )
        },
        leadingContent = { drink.image.smallImage() },
        trailingContent = { UnitAvatar(units = drink.units()) },
        modifier = Modifier.clickable { onClick() },
        tonalElevation = 8.dp,
        shadowElevation = 16.dp
    )
}
