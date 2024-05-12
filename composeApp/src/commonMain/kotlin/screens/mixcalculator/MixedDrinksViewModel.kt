package fi.tuska.beerclock.screens.mixcalculator

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.navigator.Navigator
import fi.tuska.beerclock.drinks.mix.MixedDrink
import fi.tuska.beerclock.drinks.mix.MixedDrinkInfo
import fi.tuska.beerclock.drinks.mix.MixedDrinkOverview
import fi.tuska.beerclock.drinks.mix.MixedDrinksService
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.logging.getLogger
import fi.tuska.beerclock.ui.composables.SnackbarViewModel
import fi.tuska.beerclock.ui.composables.showNotificationWithCancel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

private val logger = getLogger("MixedDrinksVM")

class MixedDrinksViewModel(val navigator: Navigator) :
    SnackbarViewModel(snackbar = SnackbarHostState()) {
    private val mixService = MixedDrinksService()

    var openedMix by mutableStateOf<MixedDrinkInfo?>(null)
        private set

    val mixedDrinkResults: StateFlow<List<MixedDrinkOverview>> =
        mixService.flowMixedDrinks()
            .stateIn(
                scope = this,
                initialValue = listOf(),
                started = SharingStarted.WhileSubscribed(5_000)
            )

    fun addNewMix() {
        navigator.push(MixedDrinkEditorScreen())
    }

    fun modifyMix(drinkMix: MixedDrinkInfo) {
        val id = drinkMix.id ?: return
        launch {
            val mix = mixService.getDrinkMix(id)
            navigator.push(MixedDrinkEditorScreen(mix))
        }
    }

    fun deleteMix(drinkMix: MixedDrinkInfo) {
        val mixId = drinkMix.id ?: return
        launch {
            val mix = mixService.getDrinkMix(mixId)
            mixService.deleteDrinkMix(mixId)
            showMixDeleted(mix)
        }
    }

    fun showMix(drinkMix: MixedDrinkInfo) {
        openedMix = drinkMix
    }

    fun closeDialog() {
        openedMix = null
    }

    private fun showMixDeleted(mix: MixedDrink) {
        val strings = Strings.get()
        showNotificationWithCancel(
            snackbar,
            strings.mixedDrinks.drinkDeleted(mix),
            strings.cancel
        ) {
            val restored = mixService.insertDrinkMix(mix)
            logger.info("Restored $restored to db")
        }
    }
}
