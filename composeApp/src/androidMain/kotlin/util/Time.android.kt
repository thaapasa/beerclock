package fi.tuska.beerclock.util

import kotlinx.datetime.LocalDate
import java.time.temporal.WeekFields
import java.util.Locale

fun LocalDate.toJavaLocalDate(): java.time.LocalDate {
    return java.time.LocalDate.of(year, month, dayOfMonth)
}

actual fun LocalDate.toWeekOfYear(): WeekOfYear {
    val w = WeekFields.of(Locale.getDefault())
    val jd = toJavaLocalDate()
    return WeekOfYear(
        weekNumber = jd.get(w.weekOfWeekBasedYear()),
        year = jd.get(w.weekBasedYear())
    )
}

