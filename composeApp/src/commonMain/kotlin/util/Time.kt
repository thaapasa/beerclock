package fi.tuska.beerclock.util

import fi.tuska.beerclock.logging.getLogger
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration
import kotlin.time.DurationUnit


private val logger = getLogger("TimeUtils")

val ZeroHour = LocalTime(0, 0, 0)
const val MinutesInDay = 60 * 24

fun LocalDate.toUTCInstant(): Instant {
    val l = LocalDateTime(date = this, time = ZeroHour)
    return l.toInstant(TimeZone.UTC)
}

fun LocalDate.toUTCEpochMillis(): Long {
    return this.toUTCInstant().toEpochMilliseconds()
}

fun LocalDateTime.Companion.fromUTCEpochMillis(millis: Long): LocalDateTime {
    return Instant.fromEpochMilliseconds(millis).toLocalDateTime(TimeZone.currentSystemDefault())
}

fun LocalDate.Companion.fromUTCEpochMillis(millis: Long): LocalDate {
    return LocalDateTime.fromUTCEpochMillis(millis).date
}

fun LocalTime.toPrefsString(): String {
    return "${this.hour.zeroPad(2)}:${this.minute.zeroPad(2)}"
}

fun LocalTime.Companion.fromPrefsString(str: String): LocalTime? {
    return try {
        val (hour, min) = str.split(":")
        LocalTime(hour.toInt(), min.toInt(), 0)
    } catch (e: Exception) {
        logger.warn("Error reading time from prefs: ${e.message}")
        null
    }
}

fun LocalTime.toMinutesOfDay(): Int {
    return this.hour * 60 + this.minute
}

fun LocalTime.Companion.fromMinutesOfDay(mof: Int): LocalTime {
    return LocalTime(mof / 60, mof % 60)
}

fun Duration.inHours(): Double {
    return toDouble(DurationUnit.HOURS)
}

data class WeekOfYear(val weekNumber: Int, val year: Int)

expect fun LocalDate.toWeekOfYear(): WeekOfYear

expect fun getFirstDayOfWeek(): DayOfWeek

fun firstDayOfWeek(weekOfYear: WeekOfYear): LocalDate {
    val someDayOfYear = LocalDate(weekOfYear.year, 1, 10)
    val dayFirst = getFirstDayOfWeek()
    var someFirstWeekDay = someDayOfYear
    while (someFirstWeekDay.dayOfWeek != dayFirst)
        someFirstWeekDay = someFirstWeekDay.minus(DatePeriod(days = 1))

    val someWeek = someFirstWeekDay.toWeekOfYear()
    val weekDiff = weekOfYear.weekNumber - someWeek.weekNumber
    return someFirstWeekDay.plus(DatePeriod(days = (weekDiff * 7)))
}
