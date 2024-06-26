package fi.tuska.beerclock.screens.mixcalculator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.drinks.mix.MixedDrink
import fi.tuska.beerclock.drinks.mix.MixedDrinkInfo
import fi.tuska.beerclock.drinks.mix.MixedDrinksService
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.localization.enspace
import fi.tuska.beerclock.ui.components.DrinkDialog
import fi.tuska.beerclock.ui.components.Gauge
import fi.tuska.beerclock.ui.components.GaugeValue
import fi.tuska.beerclock.ui.composables.ViewModel
import fi.tuska.beerclock.ui.composables.rememberWithDispose
import kotlinx.coroutines.launch


private val gap = 16.dp

@Composable
fun ColumnScope.MixedDrinkDialog(mix: MixedDrinkInfo, drinksVm: MixedDrinksViewModel) {
    val vm = rememberWithDispose { MixedDrinkDialogViewModel(mix) }
    val strings = Strings.get()
    DrinkDialog(drink = mix.asDrinkInfo(), onClose = drinksVm::closeDialog, buttonContent = {
        MixedDrinkDialogButtons(
            mix,
            onModify = drinksVm::modifyMix,
            onDelete = drinksVm::deleteMix,
            onSaveToLibrary = drinksVm::saveToLibrary,
        )
    }) {
        vm.mixData?.let { data ->
            Row {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "${
                            strings.drink.quantity(data.totalQuantityCl)
                        }$enspace${strings.drink.unitsLabeled(data.totalUnits)}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        "${strings.drink.alcoholGramsInfoLabel}: ${
                            strings.drink.quantity(data.totalAlcoholCl)
                        }",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 2.dp)
                    )
                }
                Gauge(
                    value = vm.abvGauge,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
            Text(
                strings.mixedDrinks.itemsTitle,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(bottom = 2.dp)
            )
            data.items.map {
                Text(
                    "${strings.drink.quantity(it.quantityCl)} ${it.name}${
                        if (it.abvPercentage > 0.0) " (${strings.drink.abv(it.abvPercentage)})" else ""
                    }",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        mix.instructions?.let {
            Spacer(modifier = Modifier.height(gap))
            Text(
                strings.mixedDrinks.instructionsTitle,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(bottom = 2.dp)
            )
            Text(it, style = MaterialTheme.typography.bodyMedium)
        }
    }
}


@Composable
fun MixedDrinkDialogButtons(
    mix: MixedDrinkInfo,
    onModify: (mix: MixedDrinkInfo) -> Unit,
    onDelete: (mix: MixedDrinkInfo) -> Unit,
    onSaveToLibrary: (mix: MixedDrinkInfo) -> Unit,
) {
    val strings = Strings.get()
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        IconButton({ onModify(mix) }) {
            AppIcon.DELETE.icon(
                modifier = Modifier.size(ButtonDefaults.IconSize),
                tint = MaterialTheme.colorScheme.tertiary
            )
        }
        IconButton({ onDelete(mix) }) {
            AppIcon.EDIT.icon(
                modifier = Modifier.size(ButtonDefaults.IconSize),
                tint = MaterialTheme.colorScheme.primary
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        FilledTonalButton(
            { onSaveToLibrary(mix) },
            contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
            colors = ButtonDefaults.filledTonalButtonColors(contentColor = MaterialTheme.colorScheme.primary)
        ) {
            AppIcon.DRINK.icon(modifier = Modifier.size(ButtonDefaults.IconSize))
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(strings.mixedDrinks.saveToLibrary)
        }
    }
}

class MixedDrinkDialogViewModel(private val mix: MixedDrinkInfo) : ViewModel() {
    private val mixService = MixedDrinksService()
    var mixData by mutableStateOf<MixedDrink?>(null)
        private set

    val abvGauge = GaugeValue(initialValue = 0.0, appIcon = AppIcon.BOLT, maxValue = defaultMaxAbv)

    init {
        loadData()
    }

    private fun loadData() {
        val id = mix.id ?: return
        launch {
            val data = mixService.getDrinkMix(id)
            mixData = data
            abvGauge.setValue(
                data.totalAbv,
                maxValuesByCategory[data.info.category] ?: defaultMaxAbv
            )
        }
    }
}
