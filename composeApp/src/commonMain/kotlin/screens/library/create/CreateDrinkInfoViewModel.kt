package fi.tuska.beerclock.screens.library.create

import fi.tuska.beerclock.logging.getLogger
import fi.tuska.beerclock.screens.drinks.DrinkEditorViewModel

private val logger = getLogger("NewDrinkScreen")

class CreateDrinkInfoViewModel : DrinkEditorViewModel() {

    fun saveDrink(afterChanged: (() -> Unit)? = null) {
        savingAction {
            val newDrink = toSaveDetails()
            logger.info("Adding new drink info to database: $newDrink")
            drinkService.insertDrinkInfo(newDrink)
            afterChanged?.invoke()
        }
    }
}
