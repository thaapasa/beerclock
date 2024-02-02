package fi.tuska.beerclock.screens.newdrink

import androidx.compose.foundation.clickable
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.drinks.BasicDrinkInfo
import fi.tuska.beerclock.images.smallImage
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.screens.library.CategoryHeaderInfo
import fi.tuska.beerclock.screens.library.CategoryHeaderItem
import fi.tuska.beerclock.ui.components.UnitAvatar
import fi.tuska.beerclock.ui.composables.pressable

@Composable
fun BasicDrinkItem(
    drink: BasicDrinkInfo,
    onClick: (drinkInfo: BasicDrinkInfo?) -> Unit = {},
    onLongClick: (drinkInfo: BasicDrinkInfo?) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    if (drink is TextDrinkInfo) {
        TextDrinkItem(drink, modifier = modifier)
        return
    } else if (drink is CategoryHeaderInfo) {
        CategoryHeaderItem(drink, modifier = modifier)
        return
    }

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
        modifier = modifier.pressable(
            onTap = { onClick(drink) },
            onLongPress = { onLongClick(drink) }
        ),
        leadingContent = { drink.image.smallImage() },
        trailingContent = {
            UnitAvatar(units = drink.units())
        },
        tonalElevation = 1.dp,
        shadowElevation = 16.dp
    )
}

@Composable
fun TextDrinkItem(
    drink: TextDrinkInfo,
    modifier: Modifier = Modifier,
) {
    ListItem(
        headlineContent = { Text(drink.name) },
        supportingContent = { drink.description?.let { Text(it) } },
        modifier = modifier.clickable(onClick = drink.onClick),
        trailingContent = {
            drink.icon?.icon(tint = MaterialTheme.colorScheme.primary)
        },
        tonalElevation = 1.dp,
        shadowElevation = 16.dp
    )
}
