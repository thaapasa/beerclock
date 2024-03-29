package fi.tuska.beerclock.util

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import platform.Foundation.NSCalendar
import platform.Foundation.NSCalendarUnitWeekOfYear
import platform.Foundation.NSCalendarUnitYearForWeekOfYear
import platform.Foundation.NSDateComponents

actual fun LocalDate.toWeekOfYear(): WeekOfYear {
    val calendar = NSCalendar.currentCalendar
    val date = this
    val components = NSDateComponents().apply {
        year = date.year.toLong()
        month = date.monthNumber.toLong()
        day = date.dayOfMonth.toLong()
    }
    val nsDate = calendar.dateFromComponents(components)!!
    val weekOfYear = calendar.component(NSCalendarUnitWeekOfYear, nsDate).toInt()
    val year = calendar.component(NSCalendarUnitYearForWeekOfYear, nsDate).toInt()

    return WeekOfYear(weekNumber = weekOfYear, year = year)
}

actual fun getFirstDayOfWeek(): DayOfWeek {
    val calendar = NSCalendar.currentCalendar
    val firstDay = calendar.firstWeekday().toInt()
    return when (firstDay) {
        1 -> DayOfWeek.SUNDAY
        2 -> DayOfWeek.MONDAY
        3 -> DayOfWeek.TUESDAY
        4 -> DayOfWeek.WEDNESDAY
        5 -> DayOfWeek.THURSDAY
        6 -> DayOfWeek.FRIDAY
        7 -> DayOfWeek.SATURDAY
        else -> DayOfWeek.SUNDAY
    }
}
