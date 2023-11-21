package fi.tuska.beerclock.localization

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month

object EnStrings : Strings {
    override val appName = "Beer Clock"
    override val menu = Menu

    override fun weekdayShort(day: DayOfWeek): String {
        return when (day) {
            DayOfWeek.MONDAY -> "Mon"
            DayOfWeek.TUESDAY -> "Tue"
            DayOfWeek.WEDNESDAY -> "Wed"
            DayOfWeek.THURSDAY -> "Thu"
            DayOfWeek.FRIDAY -> "Fri"
            DayOfWeek.SATURDAY -> "Sat"
            DayOfWeek.SUNDAY -> "Sun"
            else -> "?"
        }
    }

    override fun weekday(day: DayOfWeek): String {
        return when (day) {
            DayOfWeek.MONDAY -> "Monday"
            DayOfWeek.TUESDAY -> "Tuesday"
            DayOfWeek.WEDNESDAY -> "Wednesday"
            DayOfWeek.THURSDAY -> "Thursday"
            DayOfWeek.FRIDAY -> "Friday"
            DayOfWeek.SATURDAY -> "Saturday"
            DayOfWeek.SUNDAY -> "Sunday"
            else -> "?"
        }
    }

    override fun month(day: Month): String {
        return day.toString().lowercase().replaceFirstChar { it.titlecase() }
    }

    override fun date(date: LocalDateTime): String {
        val day = date.dayOfMonth
        val suffix = when (day) {
            1, 21, 31 -> "st"
            2, 22 -> "nd"
            3 -> "rd"
            else -> "th"
        }
        val mon = month(date.month).substring(0, 3)
        return "$mon $day$suffix"
    }

    object Menu : Strings.MenuStrings {
        override val main = "Menu"
        override val settings = "Settings"
        override val drinks = "Drinks"
        override val statistics = "Statistics"
    }

    override val settings = Settings

    object Settings : Strings.SettingsStrings {
        override val title = "Settings"
        override val weightLabel = "Weight in kg"
        override val genderLabel = "Gender"
    }

    override val gender = Gender

    object Gender : Strings.GenderStrings {
        override val male = "Male"
        override val female = "Female"
    }

    object NewDrinks : Strings.NewDrinkStrings {
        override val title = "Add a drink"
        override val submit = "Drink!"
    }

    override val newDrink = NewDrinks
}