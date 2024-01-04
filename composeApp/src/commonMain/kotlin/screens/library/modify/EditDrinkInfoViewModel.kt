package fi.tuska.beerclock.screens.library.modify

import fi.tuska.beerclock.drinks.DrinkInfo
import fi.tuska.beerclock.logging.getLogger
import fi.tuska.beerclock.screens.drinks.DrinkEditorViewModel

private val logger = getLogger("NewDrinkScreen")

class EditDrinkInfoViewModel(val drink: DrinkInfo) : DrinkEditorViewModel() {

    init {
        setValues(drink)
    }

    fun saveDrink(afterChanged: (() -> Unit)? = null) {
        savingAction {
            val modifiedDrink = toSaveDetails()
            logger.info("Updating drink info ${drink.id} to database: $modifiedDrink")
            drinkService.updateDrinkInfo(drink.id, modifiedDrink)
            afterChanged?.invoke()
        }
    }
}
