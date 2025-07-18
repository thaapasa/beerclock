package fi.tuska.beerclock.util

import fi.tuska.beerclock.drinks.DrinkTimeService
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.daysUntil
import kotlinx.datetime.plus
import kotlin.time.Duration
import kotlin.time.Instant

val OneYear = DatePeriod(years = 1)
val OneMonth = DatePeriod(months = 1)
val OneWeek = DatePeriod(days = 7)

/**
 * Closed-open range of time (start time is included, end is not).
 */
data class TimeInterval(val start: Instant, val end: Instant) {

    init {
        require(start <= end) { "Start of the time range must be before the end of the range." }
    }

    fun contains(instant: Instant): Boolean = instant in start..end && instant != end

    fun iterable(resolution: Duration): Iterable<Instant> {
        return Iterable { TimeIntervalIterator(start, end, resolution) }
    }

    fun lengthInDays(historicalDates: Boolean): Int {
        val times = DrinkTimeService()
        val rangeStart = times.currentDrinkDay(start)
        val rangeEnd = times.currentDrinkDay(end)
        if (!historicalDates) return rangeStart.daysUntil(rangeEnd)

        val today = times.currentDrinkDay()
        return when {
            rangeStart > today -> 0
            rangeEnd > today -> rangeStart.daysUntil(today)
            else -> rangeStart.daysUntil(rangeEnd)
        }
    }

    companion object {

        /**
         * @return closed-open range of given days (start date time is included, end date is not).
         */
        fun ofDays(startDate: LocalDate, endDate: LocalDate): TimeInterval {
            val times = DrinkTimeService()
            return TimeInterval(
                start = times.dayStartTime(startDate),
                end = times.dayStartTime(endDate)
            )
        }

        fun ofYear(year: Int): TimeInterval {
            val startDay = LocalDate(year = year, month = Month.JANUARY, day = 1)
            val endDay = LocalDate(year = year + 1, month = Month.JANUARY, day = 1)
            return ofDays(startDay, endDay)
        }

        fun ofMonth(year: Int, month: Month): TimeInterval {
            val startDay = LocalDate(year = year, month = month, day = 1)
            val endDay = startDay.plus(OneMonth)
            return ofDays(startDay, endDay)
        }

        fun ofWeek(weekOfYear: WeekOfYear): TimeInterval {
            val startDay = firstDayOfWeek(weekOfYear)
            val endDay = startDay.plus(OneWeek)
            return ofDays(startDay, endDay)
        }
    }
}


class TimeIntervalIterator(
    startTime: Instant,
    private val endTime: Instant,
    private val resolution: Duration,
) : Iterator<Instant> {

    private var currentTime = startTime

    override fun hasNext(): Boolean = currentTime < endTime

    override fun next(): Instant {
        if (!hasNext()) {
            throw NoSuchElementException()
        }
        val nextTime = currentTime
        currentTime = currentTime.plus(resolution)
        return if (nextTime < endTime) nextTime else endTime
    }
}
