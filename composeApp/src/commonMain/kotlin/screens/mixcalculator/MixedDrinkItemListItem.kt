package fi.tuska.beerclock.screens.mixcalculator

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.drinks.mix.MixedDrinkItem
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.ui.components.AppListItem
import fi.tuska.beerclock.ui.components.Gauge
import fi.tuska.beerclock.ui.components.GaugeValue
import fi.tuska.beerclock.ui.components.UnitAvatar
import fi.tuska.beerclock.ui.composables.SwipeControl

@Composable
fun MixedDrinkItemListItem(
    item: MixedDrinkItem,
    totalQuantityCl: Double,
    onModify: ((drink: MixedDrinkItem) -> Unit)? = null,
    onDelete: ((drink: MixedDrinkItem) -> Unit)? = null,
) {
    val strings = Strings.get()
    val quantityGauge = remember {
        GaugeValue(
            item.quantityCl,
            appIcon = AppIcon.GLASS_FULL,
            maxValue = totalQuantityCl
        )
    }
    LaunchedEffect(item, totalQuantityCl) {
        quantityGauge.setValue(item.quantityCl, totalQuantityCl)
    }

    SwipeControl(
        onModify = { onModify?.invoke(item) },
        onDelete = { onDelete?.invoke(item) },
    ) {
        AppListItem(
            headline = item.name,
            supporting = strings.drink.drinkSize(
                quantityCl = item.quantityCl,
                abvPercentage = item.abvPercentage
            ),
            trailingContent = {
                Row {
                    Gauge(quantityGauge, formatter = { Strings.get().drink.quantity(it) })
                    Spacer(modifier = Modifier.width(8.dp))
                    UnitAvatar(units = item.units())
                }
            },
            modifier = Modifier.clickable(onClick = { onModify?.invoke(item) })
        )
    }
}
