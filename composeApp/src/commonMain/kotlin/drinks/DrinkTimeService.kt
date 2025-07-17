package fi.tuska.beerclock.drinks

import fi.tuska.beerclock.settings.GlobalUserPreferences
import fi.tuska.beerclock.util.MinutesInDay
import fi.tuska.beerclock.util.TimeInterval
import fi.tuska.beerclock.util.fromMinutesOfDay
import fi.tuska.beerclock.util.getFirstDayOfWeek
import fi.tuska.beerclock.util.toMinutesOfDay
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import kotlin.time.Clock
import kotlin.time.Instant

/**
 * This class defines how time zones are handled and for which days each drink record is shown.
 * The user may select a different starting time for a day than the standard 0.00 (midnight),
 * in which case drinks that are consumed before that time are shown in the list for the
 * previous day.
 */
class DrinkTimeService : KoinComponent {
    private val prefs: GlobalUserPreferences = get()

    val zone = TimeZone.currentSystemDefault()

    fun now(): Instant = Clock.System.now()

    fun toInstant(time: LocalDateTime): Instant = time.toInstant(zone)
    fun toLocalDateTime(instant: Instant = now()): LocalDateTime =
        instant.toLocalDateTime(zone)

    fun firstDayOfCurrentWeek(now: LocalDate = currentDrinkDay()): LocalDate {
        val weekStart = getFirstDayOfWeek()
        var date = now
        while (date.dayOfWeek != weekStart) {
            date -= DatePeriod(days = 1)
        }
        return date
    }

    fun currentWeekRange(date: LocalDate = currentDrinkDay()): TimeInterval {
        val start = firstDayOfCurrentWeek(date)
        val end = start + DatePeriod(days = 7)
        return TimeInterval(start = dayStartTime(start), end = dayStartTime(end))
    }

    fun defaultDrinkTime(date: LocalDate?): Instant {
        if (date == null || date == currentDrinkDay()) {
            return Clock.System.now()
        }
        return toInstant(LocalDateTime(date, LocalTime(18, 0, 0)))
    }

    /**
     * Return the date which the given time is considered to be part of, as per user preferences.
     * This allows early morning hours to be considered part of yesterday.
     *
     * @return the "drinking day" for the given instant.
     */
    fun currentDrinkDay(atTime: Instant = now()): LocalDate {
        val local = toLocalDateTime(atTime)
        if (local.time < prefs.prefs.startOfDay) {
            // It's still yesterday (we'd like to think). Blimey!
            return local.date.minus(1, DateTimeUnit.DAY)
        }
        return local.date
    }

    /** @return the Instant when the given date (default=today) starts */
    fun dayStartTime(date: LocalDate = currentDrinkDay()): Instant =
        toInstant(LocalDateTime(date = date, time = prefs.prefs.startOfDay))

    /**
     * @return the Instant when the given date ends. Note: since the queries used in
     * BeerClock are always of the form closed-open (start time is included, end time is not),
     * the day end time is the same instant as the day start time for the next date.
     */
    fun dayEndTime(date: LocalDate): Instant =
        toInstant(
            LocalDateTime(
                date = date.plus(1, DateTimeUnit.DAY),
                time = prefs.prefs.startOfDay
            )
        )

    /**
     * @return the range of "drinking time" (Instants) covered by the given day. This is affected
     * by the user's preference (start tine of the day).
     */
    fun dayTimeRange(from: LocalDate, to: LocalDate = from): TimeInterval =
        TimeInterval(dayStartTime(from), dayEndTime(to))

    /**
     * For a given drinking date and time entered by user:
     * @return the real time instant
     */
    fun drinkTimeToInstant(date: LocalDate, time: LocalTime): Instant {
        val isForYesterday = time < prefs.prefs.startOfDay
        val realDate = if (isForYesterday) date.plus(1, DateTimeUnit.DAY) else date
        return toInstant(LocalDateTime(date = realDate, time = time))
    }

    /**
     * For a given real time instant
     * @return the drinking date and time
     */
    fun instantToDrinkTime(instant: Instant): Pair<LocalDate, LocalTime> {
        val local = instant.toLocalDateTime(zone)
        val isForYesterday = local.time < prefs.prefs.startOfDay
        val drinkDate = if (isForYesterday) local.date.minus(1, DateTimeUnit.DAY) else local.date
        return drinkDate to local.time
    }

    /**
     * Given the time in minutes of day, from the start of the drinking day, return the LocalTime
     * that corresponds to that time, taking the user's preference (start of drinking day) into
     * account. Used by drink time slider that allows time to be selected from start of drinking
     * day to the end of it.
     */
    fun timeFromMinuteOfDay(mof: Int): LocalTime {
        val dayStartMof = prefs.prefs.startOfDay.toMinutesOfDay()
        val minutes = (dayStartMof + mof) % MinutesInDay
        return LocalTime.fromMinutesOfDay(minutes)
    }

    /**
     * Given a local time, calculate which minute of drinking day it corresponds to, when taking
     * the user's custom start of day preference into accout. Used by drink time slider that
     * allows time to be selected from start of drinking day to the end of it.
     */
    fun toMinutesOfDay(time: LocalTime): Int {
        val dayStartMof = prefs.prefs.startOfDay.toMinutesOfDay()
        val minutes = time.toMinutesOfDay() - dayStartMof
        return if (minutes >= 0) minutes else minutes + MinutesInDay
    }
}
