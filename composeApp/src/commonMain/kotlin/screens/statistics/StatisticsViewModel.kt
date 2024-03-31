package fi.tuska.beerclock.screens.statistics

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.navigator.Navigator
import fi.tuska.beerclock.drinks.DrinkService
import fi.tuska.beerclock.drinks.DrinkTimeService
import fi.tuska.beerclock.drinks.DrinkUnitInfo
import fi.tuska.beerclock.logging.getLogger
import fi.tuska.beerclock.settings.GlobalUserPreferences
import fi.tuska.beerclock.ui.composables.ViewModel
import fi.tuska.beerclock.util.DataState
import fi.tuska.beerclock.util.TimeInterval
import fi.tuska.beerclock.util.toList
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

private val logger = getLogger("StatisticsViewModel")

class StatisticsViewModel(
    requestedPeriod: StatisticsPeriod?,
    private val navigator: Navigator,
    private val previousData: StatisticsData? = null,
) :
    ViewModel(), KoinComponent {
    val times = DrinkTimeService()
    val period = requestedPeriod ?: StatisticsWeek(times.currentDrinkDay())
    val drinkService = DrinkService()
    private val prefs: GlobalUserPreferences = get()

    init {
        logger.info("Loading statistics for $period: reference: ${period.date}, interval ${period.range}")
    }

    var statistics: DataState<StatisticsData> by mutableStateOf(DataState.Loading)

    fun displayStatistics() = statistics.valueOrNull() ?: previousData

    init {
        launch {
            val stats = drinkService.getStatisticsByCategory(period, prefs.prefs)
            val drinks = drinkService.getDrinkUnitsForPeriod(period, prefs.prefs)
            val byDates = drinks.byDates(period.range)
            val data = toStatisticsData(period, stats, byDates.toList())
            statistics = DataState.Success(data)
        }
    }

    fun asYear() = period.toYear()
    fun asMonth() = period.toMonth()
    fun asWeek() = period.toWeek()

    fun show(period: StatisticsPeriod) {
        navigator.replaceAll(StatisticsScreen(period, previousData = statistics.valueOrNull()))
    }

    fun prev() = show(period.prev())
    fun next() = show(period.next())

    private fun List<DrinkUnitInfo>.byDates(interval: TimeInterval): Map<LocalDate, Double> {
        val range = times.currentDrinkDay(interval.start)..<times.currentDrinkDay(interval.end)
        val days = range.toList()
        val res = mutableMapOf(*days.map { it to 0.0 }.toTypedArray())
        this.forEach {
            val date = times.currentDrinkDay(it.time)
            res[date] = (res[date] ?: 0.0) + it.units
        }
        return res.toMap()
    }

}
