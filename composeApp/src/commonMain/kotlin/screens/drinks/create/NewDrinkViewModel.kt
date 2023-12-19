package fi.tuska.beerclock.screens.drinks.create

import fi.tuska.beerclock.drinks.BasicDrinkInfo
import fi.tuska.beerclock.images.DrinkImage
import fi.tuska.beerclock.logging.getLogger
import fi.tuska.beerclock.screens.drinks.DrinkEditorViewModel

private val logger = getLogger("NewDrinkScreen")

class NewDrinkViewModel(proto: BasicDrinkInfo?) : DrinkEditorViewModel() {

    init {
        setValues(proto ?: NewDrinkProto)
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

object NewDrinkProto : BasicDrinkInfo(
    name = "",
    quantityCl = 10.0,
    abvPercentage = 5.0,
    image = DrinkImage.GENERIC_DRINK,
    category = null
) {
    override val key = "new-drink"
}
