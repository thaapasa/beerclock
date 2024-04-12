package fi.tuska.beerclock.screens.statistics

import fi.tuska.beerclock.drinks.DrinkTimeService
import fi.tuska.beerclock.util.CommonParcelable
import fi.tuska.beerclock.util.CommonParcelize
import fi.tuska.beerclock.util.CommonTypeParceler
import fi.tuska.beerclock.util.LocalDateParceler
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
) : CommonParcelable {
    abstract val range: TimeInterval
    private val times = DrinkTimeService()
    open fun toYear() = StatisticsYear(date)
    open fun toMonth() = StatisticsMonth(date)
    open fun toWeek() = StatisticsWeek(date)
    abstract fun next(): StatisticsPeriod
    abstract fun prev(): StatisticsPeriod
    override fun equals(other: Any?): Boolean =
        other != null && other is StatisticsPeriod && period == other.period && date == other.date
}


@CommonParcelize
class StatisticsYear(
    @CommonTypeParceler<LocalDate, LocalDateParceler>()
    private val yearDate: LocalDate,
) :
    StatisticsPeriod(yearDate, period = StatisticsPeriodType.YEAR) {
    val year = date.year
    override val range = TimeInterval.ofYear(year)
    override fun toYear() = this
    override fun toString() = "Year $year"
    override fun next() = StatisticsYear(date.plus(OneYear))
    override fun prev() = StatisticsYear(date.minus(OneYear))
}

@CommonParcelize
class StatisticsMonth(
    @CommonTypeParceler<LocalDate, LocalDateParceler>()
    private val monthDate: LocalDate,
) :
    StatisticsPeriod(
        monthDate,
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

@CommonParcelize
class StatisticsWeek(
    @CommonTypeParceler<LocalDate, LocalDateParceler>()
    private val weekDate: LocalDate,
) :
    StatisticsPeriod(
        weekDate,
        period = StatisticsPeriodType.WEEK,
    ) {
    val weekOfYear = date.toWeekOfYear()
    override val range = TimeInterval.ofWeek(weekOfYear)
    override fun toWeek() = this
    override fun toString() = "Week ${weekOfYear.year}-${weekOfYear.weekNumber.zeroPad(2)}"
    override fun next() = StatisticsWeek(date.plus(OneWeek))
    override fun prev() = StatisticsWeek(date.minus(OneWeek))
}
