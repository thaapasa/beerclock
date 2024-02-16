package fi.tuska.beerclock.screens.today

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.bac.BacFormulas.bloodAlcoholConcentration
import fi.tuska.beerclock.bac.BacStatus
import fi.tuska.beerclock.drinks.DrinkRecordInfo
import fi.tuska.beerclock.drinks.DrinkService
import fi.tuska.beerclock.drinks.DrinkTimeService
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.logging.getLogger
import fi.tuska.beerclock.settings.GlobalUserPreferences
import fi.tuska.beerclock.ui.components.BacStatusViewModel
import fi.tuska.beerclock.ui.components.DateView
import fi.tuska.beerclock.ui.components.GaugeValue
import fi.tuska.beerclock.ui.composables.SnackbarViewModel
import fi.tuska.beerclock.util.Action
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import kotlin.time.Duration.Companion.seconds

private val logger = getLogger("HomeViewModel")

val pauseBetweenUpdates = 30.seconds

class HomeViewModel(action: Action<HomeViewModel>? = null) : SnackbarViewModel(
    SnackbarHostState()
),
    BacStatusViewModel, KoinComponent {
    private val drinkService = DrinkService()
    private val times = DrinkTimeService()
    private val prefs: GlobalUserPreferences = get()

    init {
        action?.invoke(this)
    }

    private val bacGauge =
        GaugeValue(
            icon = { Text(text = "‰", color = MaterialTheme.colorScheme.primary) },
            maxValue = prefs.prefs.maxBAC
        )
    private val dailyUnitsGauge =
        GaugeValue(appIcon = AppIcon.DRINK, maxValue = prefs.prefs.maxDailyUnits)
    private val weeklyUnitsGauge =
        GaugeValue(appIcon = AppIcon.CALENDAR_WEEK, maxValue = prefs.prefs.maxWeeklyUnits)
    override val gauges = listOf(bacGauge, dailyUnitsGauge, weeklyUnitsGauge)

    var drinkDay by mutableStateOf(times.currentDrinkDay())
    val drinks = mutableStateListOf<DrinkRecordInfo>()
    var bacStatus by mutableStateOf(BacStatus(drinks, drinkDay))
        private set
    var now by mutableStateOf(Clock.System.now())
    var isYesterday by mutableStateOf(times.toLocalDateTime(now).time < prefs.prefs.startOfDay)

    init {
        updateBacContinuously()
    }

    @Composable
    override fun Content() {
        DateView(drinkDay, modifier = Modifier.padding(16.dp))
        if (isYesterday) {
            AppIcon.MOON.icon(
                modifier = Modifier.padding(top = 16.dp, start = 8.dp, end = 8.dp)
            )
        }
    }

    private fun updateBacContinuously() {
        launch {
            while (isActive) {
                delay(timeMillis = pauseBetweenUpdates.inWholeMilliseconds)
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
            val weekUnits = drinkService.getUnitsForWeek(drinkDay, prefs.prefs)
            setDrinks(newDrinks, weekUnits)
        }
    }

    private fun setDrinks(newDrinks: List<DrinkRecordInfo>, weekUnits: Double) {
        drinks.clear()
        val dayStart = times.dayStartTime(drinkDay)
        drinks.addAll(newDrinks.filter { it.time >= dayStart })
        bacStatus = BacStatus(newDrinks, drinkDay)
        dailyUnitsGauge.setValue(drinks.sumOf { it.units() }, prefs.prefs.maxDailyUnits)
        weeklyUnitsGauge.setValue(weekUnits, prefs.prefs.maxWeeklyUnits)
        updateBacStatus()
    }

    private fun updateBacStatus() {
        now = Clock.System.now()
        isYesterday = times.toLocalDateTime(now).time < prefs.prefs.startOfDay
        bacGauge.setValue(
            bloodAlcoholConcentration(
                bacStatus.atTime(now).alcoholGrams,
                prefs.prefs
            ), prefs.prefs.maxBAC
        )

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

