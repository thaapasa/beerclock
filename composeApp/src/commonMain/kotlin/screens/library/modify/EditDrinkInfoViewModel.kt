package fi.tuska.beerclock.screens.library.modify

import cafe.adriel.voyager.navigator.Navigator
import fi.tuska.beerclock.drinks.DrinkInfo
import fi.tuska.beerclock.events.DrinkInfoUpdatedEvent
import fi.tuska.beerclock.logging.getLogger
import fi.tuska.beerclock.screens.drinks.DrinkEditorViewModel

private val logger = getLogger("NewDrinkScreen")

class EditDrinkInfoViewModel(val drink: DrinkInfo, private val navigator: Navigator) :
    DrinkEditorViewModel() {

    init {
        setValues(drink)
    }

    fun saveDrink() {
        savingAction {
            val modifiedDrink = toSaveDetails()
            logger.info("Updating drink info ${drink.id} to database: $modifiedDrink")
            val updated = drinkService.updateDrinkInfo(drink.id, modifiedDrink)
            eventBus.post(DrinkInfoUpdatedEvent(updated))
            navigator.pop()
        }
    }
}
