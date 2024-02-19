package fi.tuska.beerclock.screens.statistics

import fi.tuska.beerclock.drinks.DrinkTimeService
import fi.tuska.beerclock.util.OneMonth
import fi.tuska.beerclock.util.OneWeek
import fi.tuska.beerclock.util.OneYear
import fi.tuska.beerclock.util.TimeInterval
import fi.tuska.beerclock.util.toWeekOfYear
import fi.tuska.beerclock.util.zeroPad
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus

enum class StatisticsPeriodType {
    YEAR, MONTH, WEEK
}

abstract class StatisticsPeriod(
    val date: LocalDate,
    val period: StatisticsPeriodType,
) {
    abstract val range: TimeInterval
    private val times = DrinkTimeService()
    open fun toYear() = StatisticsYear(date)
    open fun toMonth() = StatisticsMonth(date)
    open fun toWeek() = StatisticsWeek(date)
    abstract fun next(): StatisticsPeriod
    abstract fun prev(): StatisticsPeriod
}


class StatisticsYear(date: LocalDate) :
    StatisticsPeriod(date, period = StatisticsPeriodType.YEAR) {
    val year = date.year
    override val range = TimeInterval.ofYear(year)
    override fun toYear() = this
    override fun toString() = "Year $year"
    override fun next() = StatisticsYear(date.plus(OneYear))
    override fun prev() = StatisticsYear(date.minus(OneYear))
}

class StatisticsMonth(date: LocalDate) :
    StatisticsPeriod(
        date,
        period = StatisticsPeriodType.MONTH,
    ) {
    val year = date.year
    val month = date.month
    override val range = TimeInterval.ofMonth(year, month)
    override fun toMonth() = this
    override fun toString() = "Month $year/${(month.ordinal + 1).zeroPad(2)}"
    override fun next() = StatisticsMonth(date.plus(OneMonth))
    override fun prev() = StatisticsMonth(date.minus(OneMonth))
}

class StatisticsWeek(date: LocalDate) :
    StatisticsPeriod(
        date,
        period = StatisticsPeriodType.WEEK,
    ) {
    val weekOfYear = date.toWeekOfYear()
    override val range = TimeInterval.ofWeek(weekOfYear)
    override fun toWeek() = this
    override fun toString() = "Week ${weekOfYear.year}-${weekOfYear.weekNumber.zeroPad(2)}"
    override fun next() = StatisticsWeek(date.plus(OneWeek))
    override fun prev() = StatisticsWeek(date.minus(OneWeek))
}
