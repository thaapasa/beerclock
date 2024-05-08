package fi.tuska.beerclock.screens.mixcalculator

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.ui.components.DrinkDialog
import fi.tuska.beerclock.ui.composables.ViewModel
import fi.tuska.beerclock.ui.composables.rememberWithDispose
import kotlinx.coroutines.launch


private val gap = 16.dp

@Composable
fun ColumnScope.MixedDrinkDialog(mix: MixedDrinkInfo, drinksVm: MixedDrinksViewModel) {
    val vm = rememberWithDispose { MixedDrinkDialogViewModel(mix) }
    val strings = Strings.get()
    DrinkDialog(drink = mix.asDrinkInfo(), onClose = drinksVm::closeDialog) {
        vm.mixData?.let { data ->
            Text(
                strings.mixedDrinks.itemsTitle,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(bottom = 2.dp)
            )
            data.items.map {
                Text(
                    "${strings.amount(it.amount)} ${strings.drink.quantity(it.quantityCl)} ${it.name}${
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

class MixedDrinkDialogViewModel(private val mix: MixedDrinkInfo) : ViewModel() {
    private val mixService = MixedDrinksService()
    var mixData by mutableStateOf<MixedDrink?>(null)
        private set

    init {
        loadData()
    }

    private fun loadData() {
        val id = mix.id ?: return
        launch {
            val data = mixService.getDrinkMix(id)
            mixData = data
        }
    }
}
