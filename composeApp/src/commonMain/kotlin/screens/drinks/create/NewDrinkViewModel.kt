package fi.tuska.beerclock.screens.drinks.create

import fi.tuska.beerclock.drinks.exampleDrinks
import fi.tuska.beerclock.logging.getLogger
import fi.tuska.beerclock.screens.drinks.DrinkEditorViewModel

private val logger = getLogger("NewDrinkScreen")

class NewDrinkViewModel : DrinkEditorViewModel() {

    init {
        randomize()
    }

    private fun randomize() {
        val drink = exampleDrinks().random()
        setValues(drink)
    }

    fun addDrink(afterChanged: (() -> Unit)? = null) {
        savingAction {
            val newDrink = toNewDrinkRecord()
            logger.info("Adding drink to database: $newDrink")
            drinkService.insertDrink(newDrink)
            afterChanged?.invoke()
        }
    }
}
