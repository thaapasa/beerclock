package fi.tuska.beerclock.screens.newdrink

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.drinks.BasicDrinkInfo
import fi.tuska.beerclock.images.smallImage
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.ui.components.AppListItem
import fi.tuska.beerclock.ui.components.UnitAvatar

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BasicDrinkItem(
    drink: BasicDrinkInfo,
    onClick: (drinkInfo: BasicDrinkInfo?) -> Unit = {},
    onLongClick: (drinkInfo: BasicDrinkInfo?) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val strings = Strings.get()
    AppListItem(
        overline = drink.producer.ifBlank { null },
        headline = drink.name,
        supporting = strings.drink.drinkSize(
            quantityCl = drink.quantityCl,
            abvPercentage = drink.abvPercentage
        ),
        modifier = modifier.combinedClickable(
            onClick = { onClick(drink) },
            onLongClick = { onLongClick(drink) }),
        icon = { drink.image.smallImage() },
        trailingContent = { UnitAvatar(units = drink.units()) },
        tonalElevation = 1.dp,
    )
}
