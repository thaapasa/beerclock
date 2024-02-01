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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import kotlin.math.max
import kotlin.math.min
import kotlin.time.Duration.Companion.seconds

private val logger = getLogger("HomeViewModel")

val pauseBetweenUpdates = 30.seconds

class HomeViewModel : ViewModel(), KoinComponent {
    private val drinkService = DrinkService()
    private val times = DrinkTimeService()
    private val prefs: GlobalUserPreferences = get()

    var drinkDay by mutableStateOf(times.currentDrinkDay())
    val drinks = mutableStateListOf<DrinkRecordInfo>()
    var bacStatus by mutableStateOf(BacStatus(drinks, drinkDay))
        private set
    var now by mutableStateOf(Clock.System.now())
    var isYesterday by mutableStateOf(times.toLocalDateTime(now).time < prefs.prefs.startOfDay)

    var units by mutableStateOf(0.0f)
    var unitsPosition by mutableStateOf(0.0f)

    var bac by mutableStateOf(0.0f)
    var bacPosition by mutableStateOf(0.0f)

    init {
        updateBacContinuously()
    }

    private fun updateBacContinuously() {
        launch {
            while (isActive) {
                delay(pauseBetweenUpdates.inWholeMilliseconds)
                logger.info("Recalculating BAC")
                updateBacStatus()
            }
        }
    }

    fun loadTodaysDrinks() {
        launch {
            drinkDay = times.currentDrinkDay()
            logger.info("Loading today's drinks for $drinkDay")
            val newDrinks = drinkService.getDrinksForHomeScreen(drinkDay)
            setDrinks(newDrinks)
        }
    }


    fun maxBACValue(): Double = max(prefs.prefs.maxBAC, 0.1)
    fun maxDailyUnitsValue(): Double = max(prefs.prefs.maxDailyUnits, 0.1)


    private fun setDrinks(newDrinks: List<DrinkRecordInfo>) {
        drinks.clear()
        val dayStart = times.dayStartTime(drinkDay)
        drinks.addAll(newDrinks.filter { it.time >= dayStart })
        bacStatus = BacStatus(newDrinks, drinkDay)
        units = drinks.sumOf { it.units() }.toFloat()
        unitsPosition = min(units / maxDailyUnitsValue(), 1.0).toFloat()
        updateBacStatus()
    }

    fun updateBacStatus() {
        now = Clock.System.now()
        isYesterday = times.toLocalDateTime(now).time < prefs.prefs.startOfDay
        bac = bloodAlcoholConcentration(
            bacStatus.atTime(now).alcoholGrams,
            prefs.prefs
        ).toFloat()
        bacPosition = min(bac / maxBACValue(), 1.0).toFloat()

        if (drinkDay != times.currentDrinkDay()) {
            logger.info("New drink day starts, reload drinks")
            loadTodaysDrinks()
        }
    }

    fun reload() {
        logger.info("Reloading drinks")
        loadTodaysDrinks()
    }
}
