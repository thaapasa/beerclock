package fi.tuska.beerclock.screens.statistics

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.navigator.Navigator
import fi.tuska.beerclock.drinks.CategoryStatistics
import fi.tuska.beerclock.drinks.DrinkService
import fi.tuska.beerclock.drinks.DrinkTimeService
import fi.tuska.beerclock.logging.getLogger
import fi.tuska.beerclock.settings.GlobalUserPreferences
import fi.tuska.beerclock.ui.composables.ViewModel
import fi.tuska.beerclock.util.DataState
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

private val logger = getLogger("StatisticsViewModel")

class StatisticsViewModel(requestedPeriod: StatisticsPeriod?, private val navigator: Navigator) :
    ViewModel(), KoinComponent {
    val times = DrinkTimeService()
    val period = requestedPeriod ?: StatisticsYear(times.currentDrinkDay())
    val drinkService = DrinkService()
    private val prefs: GlobalUserPreferences = get()

    init {
        logger.info("Loading statistics for $period: reference: ${period.date}, interval ${period.range}")
    }

    var statistics: DataState<List<CategoryStatistics>> by mutableStateOf(DataState.Loading)

    init {
        launch {
            val stats = drinkService.getStatisticsByCategory(period.range, prefs.prefs)
            statistics = DataState.Success(stats)
        }
    }

    fun asYear() = period.toYear()
    fun asMonth() = period.toMonth()
    fun asWeek() = period.toWeek()

    fun show(period: StatisticsPeriod) {
        navigator.replaceAll(StatisticsScreen(period))
    }

    fun prev() = show(period.prev())
    fun next() = show(period.next())
}