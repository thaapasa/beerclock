package fi.tuska.beerclock.screens.drinks.create

import cafe.adriel.voyager.navigator.Navigator
import fi.tuska.beerclock.drinks.BasicDrinkInfo
import fi.tuska.beerclock.drinks.DrinkTimeService
import fi.tuska.beerclock.events.DrinkRecordAddedEvent
import fi.tuska.beerclock.images.DrinkImage
import fi.tuska.beerclock.logging.getLogger
import fi.tuska.beerclock.screens.drinks.DrinkEditorViewModel
import kotlinx.datetime.LocalDate

private val logger = getLogger("NewDrinkScreen")

class NewDrinkViewModel(
    proto: BasicDrinkInfo?,
    date: LocalDate?,
    private val navigator: Navigator,
) : DrinkEditorViewModel() {

    val times = DrinkTimeService()

    init {
        setValues(proto ?: NewDrinkProto, times.defaultDrinkTime(date ?: times.currentDrinkDay()))
    }

    fun addDrink() {
        savingAction {
            val newDrink = toSaveDetails()
            logger.info("Adding drink to database: $newDrink")
            val d = drinkService.insertDrink(newDrink)
            eventBus.post(DrinkRecordAddedEvent(d))
            navigator.pop()
        }
    }
}

object NewDrinkProto : BasicDrinkInfo(
    key = "new-drink",
    name = "",
    quantityCl = 10.0,
    abvPercentage = 5.0,
    image = DrinkImage.GENERIC_DRINK,
    category = null,
)
