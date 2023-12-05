package fi.tuska.beerclock.screens.newdrink

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import fi.tuska.beerclock.bac.BacFormulas
import fi.tuska.beerclock.database.DrinkRecord
import fi.tuska.beerclock.drinks.DrinkService
import fi.tuska.beerclock.drinks.DrinkTimeService
import fi.tuska.beerclock.drinks.NewDrinkRecord
import fi.tuska.beerclock.drinks.exampleDrinks
import fi.tuska.beerclock.images.DrinkImage
import fi.tuska.beerclock.logging.getLogger
import fi.tuska.beerclock.settings.GlobalUserPreferences
import fi.tuska.beerclock.ui.composables.ViewModel
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

private val logger = getLogger("NewDrinkScreen")

class NewDrinkViewModel : ViewModel(), KoinComponent {
    private val drinkService = DrinkService()
    private val times = DrinkTimeService()
    private val drinkTime = times.instantToDrinkTime(Clock.System.now())
    private val prefs: GlobalUserPreferences = get()

    val drinks = mutableStateListOf<DrinkRecord>()
    var name by mutableStateOf("")
    var abv by mutableStateOf(4.5)
    var quantityCl by mutableStateOf(33.0)
    var date by mutableStateOf(drinkTime.first)
    var time by mutableStateOf(drinkTime.second)
    var image by mutableStateOf(DrinkImage.GENERIC_DRINK)
    var saving by mutableStateOf(false)

    init {
        randomize()
    }

    fun realTime(): Instant = times.drinkTimeToInstant(date, time)
    fun localRealTime() = times.toLocalDateTime(realTime())
    fun units(): Double = BacFormulas.getUnitsFromDisplayQuantityAbv(
        quantityCl = quantityCl,
        abvPercentage = abv,
        prefs = prefs.prefs
    )

    fun randomize() {
        val drink = exampleDrinks().random()
        name = drink.name
        quantityCl = drink.quantityCl
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

    fun addDrink(afterChanged: (() -> Unit)? = null) {
        launch {
            if (saving) return@launch
            saving = true
            try {
                val newDrink = toNewDrinkRecord()
                logger.info("Adding drink to database: $newDrink")
                drinkService.insertDrink(newDrink)
                afterChanged?.invoke()
            } finally {
                saving = false
            }
        }
    }
}
