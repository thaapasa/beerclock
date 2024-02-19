package fi.tuska.beerclock.screens.statistics

import cafe.adriel.voyager.navigator.Navigator
import fi.tuska.beerclock.drinks.DrinkTimeService
import fi.tuska.beerclock.logging.getLogger
import fi.tuska.beerclock.ui.composables.ViewModel

private val logger = getLogger("StatisticsViewModel")

class StatisticsViewModel(requestedPeriod: StatisticsPeriod?, private val navigator: Navigator) :
    ViewModel() {
    val times = DrinkTimeService()
    val period = requestedPeriod ?: StatisticsYear(times.currentDrinkDay())

    init {
        logger.info("Loading statistics for $period: reference: ${period.date}, interval ${period.range}")
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