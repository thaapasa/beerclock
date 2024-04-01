package fi.tuska.beerclock.ui.composables

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import fi.tuska.beerclock.drinks.DrinkDetailsFromEditor
import fi.tuska.beerclock.drinks.DrinkRecordInfo
import fi.tuska.beerclock.drinks.DrinkService
import fi.tuska.beerclock.events.DrinkRecordAddedEvent
import fi.tuska.beerclock.events.DrinkRecordDeletedEvent
import fi.tuska.beerclock.events.DrinkRecordUpdatedEvent
import fi.tuska.beerclock.events.EventObserver
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.logging.getLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent

private val logger = getLogger("DrinkStatusViewModel")

open class DrinkObservingViewModel(
    snackbar: SnackbarHostState,
) : SnackbarViewModel(snackbar),
    KoinComponent {

    protected val drinkService = DrinkService()
    protected open fun invalidateDrinks() {}

    protected val eventObserver = EventObserver(scope())

    init {
        // Show notifications when drinks have been added
        eventObserver.observeEventsOfType<DrinkRecordAddedEvent> { showDrinkAdded(it.drink) }
        // Show notifications when drinks have been deleted
        eventObserver.observeEventsOfType<DrinkRecordDeletedEvent> { showDrinkDeleted(it.drink) }
        // Consume drink updated event (not shown)
        eventObserver.observeEventsOfType<DrinkRecordUpdatedEvent> { }
    }

    private fun showDrinkAdded(drink: DrinkRecordInfo) {
        val strings = Strings.get()
        launch(Dispatchers.Main) {
            val result =
                snackbar.showSnackbar(
                    strings.drink.drinkAdded(drink),
                    actionLabel = strings.remove,
                    duration = SnackbarDuration.Short
                )
            if (result == SnackbarResult.ActionPerformed) {
                withContext(Dispatchers.IO) {
                    drinkService.deleteDrinkById(drink.id)
                    logger.info("Deleted $drink")
                    invalidateDrinks()
                }
            }
        }
    }

    private fun showDrinkDeleted(drink: DrinkRecordInfo) {
        val strings = Strings.get()
        launch(Dispatchers.Main) {
            val result =
                snackbar.showSnackbar(
                    strings.drink.drinkDeleted(drink),
                    actionLabel = strings.cancel,
                    duration = SnackbarDuration.Short
                )
            if (result == SnackbarResult.ActionPerformed) {
                // Remove added drink
                withContext(Dispatchers.IO) {
                    val restored =
                        drinkService.insertDrink(DrinkDetailsFromEditor.fromRecord(drink))
                    logger.info("Restored $restored to db")
                    invalidateDrinks()
                }
            }
        }
    }
}
