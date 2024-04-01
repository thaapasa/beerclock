package fi.tuska.beerclock.screens.library.create

import cafe.adriel.voyager.navigator.Navigator
import fi.tuska.beerclock.events.DrinkInfoAddedEvent
import fi.tuska.beerclock.logging.getLogger
import fi.tuska.beerclock.screens.drinks.DrinkEditorViewModel

private val logger = getLogger("NewDrinkScreen")

class CreateDrinkInfoViewModel(private val navigator: Navigator) : DrinkEditorViewModel() {

    fun saveDrink() {
        savingAction {
            val newDrink = toSaveDetails()
            logger.info("Adding new drink info to database: $newDrink")
            val created = drinkService.insertDrinkInfo(newDrink)
            eventBus.post(DrinkInfoAddedEvent(created))
            navigator.pop()
        }
    }
}
