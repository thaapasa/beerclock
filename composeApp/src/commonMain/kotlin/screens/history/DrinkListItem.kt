package fi.tuska.beerclock.screens.history

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import fi.tuska.beerclock.drinks.DrinkRecordInfo
import fi.tuska.beerclock.images.smallImage
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.ui.components.AppListItem
import fi.tuska.beerclock.ui.components.UnitAvatar
import fi.tuska.beerclock.ui.composables.SwipeControl

@Composable
fun DrinkListItem(
    drink: DrinkRecordInfo,
    onModify: ((drink: DrinkRecordInfo) -> Unit)? = null,
    onDelete: ((drink: DrinkRecordInfo) -> Unit)? = null,
) {
    val strings = Strings.get()
    var selected by remember { mutableStateOf(false) }
    SwipeControl(
        onModify = { onModify?.invoke(drink) },
        onDelete = { onDelete?.invoke(drink) },
    ) {
        AppListItem(
            overline = strings.drink.drinkTime(drink.time),
            headline = drink.name,
            supporting = strings.drink.drinkSize(
                quantityCl = drink.quantityCl,
                abvPercentage = drink.abvPercentage
            ),
            icon = { drink.image.smallImage() },
            trailingContent = { UnitAvatar(units = drink.units()) },
            modifier = Modifier.clickable { selected = !selected },
        )
    }

    if (selected) {
        DrinkInfoDialog(
            drink,
            onClose = { selected = false },
            onDelete = onDelete,
            onModify = onModify
        )
    }
}
