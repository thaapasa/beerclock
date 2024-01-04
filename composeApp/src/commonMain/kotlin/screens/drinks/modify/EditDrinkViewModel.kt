package fi.tuska.beerclock.screens.drinks.modify

import fi.tuska.beerclock.drinks.DrinkRecordInfo
import fi.tuska.beerclock.logging.getLogger
import fi.tuska.beerclock.screens.drinks.DrinkEditorViewModel

private val logger = getLogger("NewDrinkScreen")

class EditDrinkViewModel(val drink: DrinkRecordInfo) : DrinkEditorViewModel() {

    init {
        setValues(drink, drink.time)
    }

    fun saveDrink(afterChanged: (() -> Unit)? = null) {
        savingAction {
            val modifiedDrink = toSaveDetails()
            logger.info("Updating drink ${drink.id} to database: $modifiedDrink")
            drinkService.updateDrinkRecord(drink.id, modifiedDrink)
            afterChanged?.invoke()
        }
    }
}
