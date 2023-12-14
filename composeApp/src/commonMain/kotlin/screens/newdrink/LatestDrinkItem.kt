package fi.tuska.beerclock.screens.newdrink

import androidx.compose.foundation.clickable
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.drinks.LatestDrinkInfo
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.ui.components.UnitAvatar

@Composable
fun LatestDrinkItem(
    drink: LatestDrinkInfo,
    onAddDrink: (drink: LatestDrinkInfo?) -> Unit,
) {
    val strings = Strings.get()
    ListItem(
        headlineContent = { Text(drink.name) },
        supportingContent = {
            Text(
                strings.drink.drinkSize(
                    quantityCl = drink.quantityCl,
                    abvPercentage = drink.abvPercentage
                )
            )
        },
        modifier = Modifier.clickable { onAddDrink(drink) },
        leadingContent = { drink.image.smallImage() },
        trailingContent = { UnitAvatar(units = drink.units()) },
        tonalElevation = 1.dp,
        shadowElevation = 16.dp
    )
}
