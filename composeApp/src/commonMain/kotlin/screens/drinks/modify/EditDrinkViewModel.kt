package fi.tuska.beerclock.screens.drinks.modify

import cafe.adriel.voyager.navigator.Navigator
import fi.tuska.beerclock.drinks.DrinkRecordInfo
import fi.tuska.beerclock.events.DrinkRecordUpdatedEvent
import fi.tuska.beerclock.logging.getLogger
import fi.tuska.beerclock.screens.drinks.DrinkEditorViewModel

private val logger = getLogger("NewDrinkScreen")

class EditDrinkViewModel(val drink: DrinkRecordInfo, private val navigator: Navigator) :
    DrinkEditorViewModel() {

    init {
        setValues(drink, drink.time)
    }

    fun saveDrink() {
        savingAction {
            val modifiedDrink = toSaveDetails()
            logger.info("Updating drink ${drink.id} to database: $modifiedDrink")
            val updated = drinkService.updateDrinkRecord(drink.id, modifiedDrink)
            eventBus.post(DrinkRecordUpdatedEvent(updated))
            navigator.pop()
        }
    }
}
