package fi.tuska.beerclock.util

import fi.tuska.beerclock.logging.getLogger
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime


private val logger = getLogger("TimeUtils")

val ZeroHour = LocalTime(0, 0, 0)
val MinutesInDay = 60 * 24

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

/**
 * Closed-open range of time (start time is included, end is not).
 */
data class InstantRange(val start: Instant, val end: Instant) {
    init {
        require(start <= end) { "Start of the time range must be before the end of the range." }
    }

    fun contains(instant: Instant): Boolean = instant in start..end && instant != end
}
