package fi.tuska.beerclock.util

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.number
import java.time.temporal.WeekFields
import java.util.Calendar
import java.util.Locale

fun LocalDate.toJavaLocalDate(): java.time.LocalDate {
    return java.time.LocalDate.of(year, month.number, day)
}

actual fun LocalDate.toWeekOfYear(): WeekOfYear {
    val w = WeekFields.of(Locale.getDefault())
    val jd = toJavaLocalDate()
    return WeekOfYear(
        weekNumber = jd.get(w.weekOfWeekBasedYear()),
        year = jd.get(w.weekBasedYear())
    )
}

actual fun getFirstDayOfWeek(): DayOfWeek {
    val calendar = Calendar.getInstance()
    return when (calendar.firstDayOfWeek) {
        Calendar.MONDAY -> DayOfWeek.MONDAY
        Calendar.TUESDAY -> DayOfWeek.TUESDAY
        Calendar.WEDNESDAY -> DayOfWeek.WEDNESDAY
        Calendar.THURSDAY -> DayOfWeek.THURSDAY
        Calendar.FRIDAY -> DayOfWeek.FRIDAY
        Calendar.SATURDAY -> DayOfWeek.SATURDAY
        Calendar.SUNDAY -> DayOfWeek.SUNDAY
        else -> {
            throw RuntimeException("Calendar first day of week is invalid")
        }
    }
}
