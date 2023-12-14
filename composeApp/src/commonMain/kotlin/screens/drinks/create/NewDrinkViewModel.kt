package fi.tuska.beerclock.screens.drinks.create

import fi.tuska.beerclock.drinks.BasicDrinkInfo
import fi.tuska.beerclock.drinks.exampleDrinks
import fi.tuska.beerclock.logging.getLogger
import fi.tuska.beerclock.screens.drinks.DrinkEditorViewModel

private val logger = getLogger("NewDrinkScreen")

class NewDrinkViewModel(proto: BasicDrinkInfo?) : DrinkEditorViewModel() {

    init {
        if (proto != null) {
            setValues(proto)
        } else {
            randomize()
        }
    }

    private fun randomize() {
        val drink = exampleDrinks().random()
        setValues(drink)
    }

    fun addDrink(afterChanged: (() -> Unit)? = null) {
        savingAction {
            val newDrink = toSaveDetails()
            logger.info("Adding drink to database: $newDrink")
            drinkService.insertDrink(newDrink)
            afterChanged?.invoke()
        }
    }
}
