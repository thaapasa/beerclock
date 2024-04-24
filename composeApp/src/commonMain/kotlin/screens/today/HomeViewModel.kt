package fi.tuska.beerclock.screens.today

import androidx.compose.foundation.layout.padding
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
import fi.tuska.beerclock.drinks.DrinkTimeService
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.logging.getLogger
import fi.tuska.beerclock.settings.GlobalUserPreferences
import fi.tuska.beerclock.ui.components.BacStatusViewModel
import fi.tuska.beerclock.ui.components.DateView
import fi.tuska.beerclock.ui.components.GaugeValueWithHelp
import fi.tuska.beerclock.ui.composables.DrinkObservingViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import kotlin.time.Duration.Companion.seconds

private val logger = getLogger("HomeViewModel")

val pauseBetweenUpdates = 30.seconds

class HomeViewModel : DrinkObservingViewModel(SnackbarHostState()),
    BacStatusViewModel, KoinComponent {
    private val times = DrinkTimeService()
    private val prefs: GlobalUserPreferences = get()
    private val strings = Strings.get()

    private val bacGauge =
        GaugeValueWithHelp(
            icon = { Text(text = "‰", color = it) },
            maxValue = prefs.prefs.maxBac,
            helpText = strings.help.bacStatusGauge,
        )
    private val dailyUnitsGauge =
        GaugeValueWithHelp(
            appIcon = AppIcon.DRINK,
            maxValue = prefs.prefs.maxDailyUnits,
            helpText = strings.help.dailyUnitsGauge,
        )
    private val weeklyUnitsGauge =
        GaugeValueWithHelp(
            appIcon = AppIcon.CALENDAR_WEEK,
            maxValue = prefs.prefs.maxWeeklyUnits,
            helpText = strings.help.weeklyUnitsGauge,
        )
    override val gauges = listOf(bacGauge, dailyUnitsGauge, weeklyUnitsGauge)

    var drinkDay by mutableStateOf(times.currentDrinkDay())
    val drinks = mutableStateListOf<DrinkRecordInfo>()
    var bacStatus by mutableStateOf(BacStatus(drinks, drinkDay))
        private set
    var now by mutableStateOf(Clock.System.now())
    var isYesterday by mutableStateOf(times.toLocalDateTime(now).time < prefs.prefs.startOfDay)
        private set

    init {
        updateBacContinuously()
    }

    fun canDrive(): Boolean {
        return bacGauge.value < 0.01
    }

    fun timeWhenSober(): Instant = bacStatus.timeWhenBacAt(0.0)

    @Composable
    override fun Content() {
        DateView(drinkDay, modifier = Modifier.padding(16.dp))
    }

    override suspend fun invalidateDrinks() {
        loadTodaysDrinks()
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

    suspend fun loadTodaysDrinks() {
        drinkDay = times.currentDrinkDay()
        logger.info("Loading today's drinks for $drinkDay")
        val newDrinks = drinkService.getDrinksForHomeScreen(drinkDay)
        val weekUnits = drinkService.getUnitsForWeek(drinkDay, prefs.prefs)
        setDrinks(newDrinks, weekUnits)
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
            ), prefs.prefs.maxBac
        )

        if (drinkDay != times.currentDrinkDay()) {
            logger.info("New drink day starts, reload drinks")
            launch {
                loadTodaysDrinks()
            }
        }
    }
}

