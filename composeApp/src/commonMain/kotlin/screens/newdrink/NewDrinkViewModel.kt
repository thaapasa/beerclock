package fi.tuska.beerclock.screens.newdrink

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.navigator.Navigator
import fi.tuska.beerclock.database.DrinkRecord
import fi.tuska.beerclock.drinks.DrinkService
import fi.tuska.beerclock.drinks.DrinkTimeService
import fi.tuska.beerclock.drinks.ExampleDrinks
import fi.tuska.beerclock.drinks.NewDrinkRecord
import fi.tuska.beerclock.images.DrinkImage
import fi.tuska.beerclock.logging.getLogger
import fi.tuska.beerclock.ui.composables.ViewModel
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

private val logger = getLogger("NewDrinkScreen")

class NewDrinkViewModel(
    private val drinkService: DrinkService,
    private val navigator: Navigator
) : ViewModel() {
    private val times = DrinkTimeService()
    private val drinkTime = times.instantToDrinkTime(Clock.System.now())
    val drinks = mutableStateListOf<DrinkRecord>()
    var name by mutableStateOf("")
    var abv by mutableStateOf(4.5)
    var quantityCl by mutableStateOf(33.0)
    var date by mutableStateOf(drinkTime.first)
    var time by mutableStateOf(drinkTime.second)
    var image by mutableStateOf(DrinkImage.GENERIC_DRINK)

    init {
        randomize()
    }

    fun realTime(): Instant = times.drinkTimeToInstant(date, time)
    fun localRealTime() = times.toLocalDateTime(realTime())

    private fun randomize() {
        val drink = ExampleDrinks.random()
        name = drink.name
        quantityCl = drink.quantityLiters * 100
        abv = drink.abvPercentage
        image = drink.image
    }

    private fun toNewDrinkRecord(): NewDrinkRecord {
        return NewDrinkRecord(
            time = realTime(),
            name = name,
            abv = abv / 100.0,
            quantityLiters = quantityCl / 100,
            image = image,
        )
    }

    fun addDrink() {
        launch {
            val newDrink = toNewDrinkRecord()
            logger.info("Adding drink to database: $newDrink")
            drinkService.insertDrink(newDrink)
            navigator.pop()
        }
    }
}
