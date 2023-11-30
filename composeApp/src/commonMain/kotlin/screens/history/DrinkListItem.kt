package fi.tuska.beerclock.screens.history

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
        leadingContent = {
            Image(
                painter = drink.image.painter(),
                contentDescription = "Beer",
                modifier = Modifier.width(64.dp).clip(RoundedCornerShape(12.dp)),
            )
        },
        trailingContent = { UnitAvatar(units = 1.1) },
        modifier = Modifier.clickable { onClick() },
        tonalElevation = 8.dp,
        shadowElevation = 16.dp
    )
}
