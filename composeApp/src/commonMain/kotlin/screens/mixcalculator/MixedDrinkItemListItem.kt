import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import fi.tuska.beerclock.drinks.mix.MixedDrinkItem
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.ui.components.AppListItem
import fi.tuska.beerclock.ui.components.UnitAvatar
import fi.tuska.beerclock.ui.composables.SwipeControl

@Composable
fun MixedDrinkItemListItem(
    item: MixedDrinkItem,
    onModify: ((drink: MixedDrinkItem) -> Unit)? = null,
    onDelete: ((drink: MixedDrinkItem) -> Unit)? = null,
) {
    val strings = Strings.get()
    var selected by remember { mutableStateOf(false) }
    SwipeControl(
        onModify = { onModify?.invoke(item) },
        onDelete = { onDelete?.invoke(item) },
    ) {
        AppListItem(
            headline = "${item.amount} x ${item.name}",
            supporting = strings.drink.drinkSize(
                quantityCl = item.quantityCl,
                abvPercentage = item.abvPercentage
            ),
            trailingContent = { UnitAvatar(units = 0.4) },
            modifier = Modifier.clickable { selected = !selected },
        )
    }
}
