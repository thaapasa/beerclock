package fi.tuska.beerclock.screens.mixcalculator

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.navigator.Navigator
import fi.tuska.beerclock.drinks.DrinkInfo
import fi.tuska.beerclock.drinks.DrinkService
import fi.tuska.beerclock.drinks.DrinkTimeService
import fi.tuska.beerclock.drinks.mix.MixedDrink
import fi.tuska.beerclock.drinks.mix.MixedDrinkInfo
import fi.tuska.beerclock.drinks.mix.MixedDrinkOverview
import fi.tuska.beerclock.drinks.mix.MixedDrinksService
import fi.tuska.beerclock.events.DrinkInfoAddedEvent
import fi.tuska.beerclock.events.EventObserver
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.logging.getLogger
import fi.tuska.beerclock.screens.library.create.CreateDrinkInfoScreen
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
    private val times = DrinkTimeService()
    private val drinks = DrinkService()

    var openedMix by mutableStateOf<MixedDrinkInfo?>(null)
        private set

    init {
        val observer = EventObserver(scope())
        observer.observeEventsOfType<DrinkInfoAddedEvent> { showDrinkAdded(it.drink) }
    }

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
        closeDialog()
        launch {
            val mix = mixService.getDrinkMix(mixId)
            mixService.deleteDrinkMix(mixId)
            showMixDeleted(mix)
        }
    }

    fun saveToLibrary(drinkMix: MixedDrinkInfo) {
        val mixId = drinkMix.id ?: return
        closeDialog()
        launch {
            val mix = mixService.getDrinkMix(mixId)
            navigator.push(CreateDrinkInfoScreen(proto = mix.asDrinkInfo()))
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

    private fun showDrinkAdded(drink: DrinkInfo) {
        val strings = Strings.get()
        showNotificationWithCancel(
            snackbar,
            strings.library.drinkAdded(drink),
            strings.remove
        ) {
            drinks.deleteDrinkInfoById(drink.id)
            logger.info("Deleted $drink from db")
        }
    }
}
