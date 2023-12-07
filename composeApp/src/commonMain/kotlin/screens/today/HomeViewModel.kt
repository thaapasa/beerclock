package fi.tuska.beerclock.screens.today

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import fi.tuska.beerclock.bac.BacFormulas.bloodAlcoholConcentration
import fi.tuska.beerclock.bac.BacStatus
import fi.tuska.beerclock.drinks.DrinkRecordInfo
import fi.tuska.beerclock.drinks.DrinkService
import fi.tuska.beerclock.drinks.DrinkTimeService
import fi.tuska.beerclock.logging.getLogger
import fi.tuska.beerclock.settings.GlobalUserPreferences
import fi.tuska.beerclock.ui.composables.ViewModel
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import kotlin.math.min

private val logger = getLogger("HomeViewModel")

val maxUnitsPosition = 7.0
val maxBacPosition = 1.0

class HomeViewModel : ViewModel(), KoinComponent {
    private val drinkService = DrinkService()
    private val times = DrinkTimeService()
    private val prefs: GlobalUserPreferences = get()
    val drinks = mutableStateListOf<DrinkRecordInfo>()
    var bacStatus by mutableStateOf(BacStatus(drinks))
        private set

    fun units(): Double = drinks.sumOf { it.units() }
    fun unitsPosition(): Double = min(units() / maxUnitsPosition, 1.0)

    fun bac(): Double =
        bloodAlcoholConcentration(bacStatus.atTime(Clock.System.now()).alcoholGrams, prefs.prefs)

    fun bacPosition(): Double = min(bac() / maxBacPosition, 1.0)

    fun loadTodaysDrinks() {
        launch {
            drinks.clear()
            val newDrinks = drinkService.getDrinksForHomeScreen()
            val dayStart = times.dayStartTime()
            bacStatus = BacStatus(newDrinks)
            drinks.addAll(newDrinks.filter { it.time >= dayStart })
        }
    }

    fun reload() {
        logger.info("Reloading drinks")
        loadTodaysDrinks()
    }
}
