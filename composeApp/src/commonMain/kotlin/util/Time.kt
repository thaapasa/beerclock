package fi.tuska.beerclock.util

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime


val ZeroHour = LocalTime(0, 0, 0)

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
