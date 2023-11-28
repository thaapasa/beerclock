package fi.tuska.beerclock.localization

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

object EnStrings : Strings {
    override val appName = "Beer Clock"

    override fun createNumberFormatter(digits: Int) = getDecimalFormatter(
        maximumFractionDigits = digits,
        decimalSeparator = '.',
        isGroupingUsed = true,
        groupingSeparator = ','
    )

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

    override fun month(day: Month): String {
        return day.toString().lowercase().replaceFirstChar { it.titlecase() }
    }

    override fun date(date: LocalDate): String {
        return "${dateShort(date)} ${date.year}"
    }

    override fun dateShort(date: LocalDate): String {
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

    override fun time(time: LocalTime): String {
        return "${time.hour}.${time.minute.toString().padStart(2, '0')}"
    }

    override val pickTime = "Pick time"
    override val pickDate = "Pick date"


    /* Main menu */
    override val menu = Menu

    object Menu : Strings.MenuStrings {
        override val goBack = "Go back"
        override val menu = "Menu"
        override val today = "Today"
        override val settings = "Settings"
        override val history = "History"
        override val statistics = "Statistics"
    }


    /* Drink info */
    override val drink = DrinkData

    object DrinkData : Strings.DrinkData {
        override val unitLabel = "units"
        val unitF = createNumberFormatter(2)
        val abvF = createNumberFormatter(1)
        val quantityF = createNumberFormatter(1)
        override fun abv(abv: Double) = "${abvF(abv)} %"
        override fun units(units: Double) = unitF(units)
        override fun quantity(quantity: Double) = "${quantityF(quantity)} l"
        override fun itemDescription(quantity: Double, abv: Double): String =
            "${quantityF(quantity)} l ${abvF(abv)} %"

        override fun drinkTime(time: Instant): String =
            time.toLocalDateTime(TimeZone.currentSystemDefault())
                .let { "${it.hour}:${it.minute.toString().padStart(2, '0')}" }

    }


    /* Error strings */
    override val errors = Errors

    object Errors : Strings.ErrorStrings {
        override val invalidDecimal = "Not a valid decimal value"
    }


    /* Settings screen */
    override val settings = Settings

    object Settings : Strings.SettingsStrings {
        override val title = "Settings"
        override val weightLabel = "Weight in kg"
        override val genderLabel = "Gender"
    }


    /* New drink screen */
    override val newDrink = NewDrinks

    object NewDrinks : Strings.NewDrinkStrings {
        override val title = "Add a drink"
        override val dateLabel = "Drinking day"
        override val timeLabel = "Time of day"
        override val nameLabel = "Name"
        override val abvLabel = "Alcohol by volume"
        override val abvUnit = "%"
        override val quantityLabel = "Quantity"
        override val quantityUnit = "cl"
        override val submit = "Drink!"
    }


    /* Gender options */
    override val gender = Gender

    object Gender : Strings.GenderStrings {
        override val male = "Male"
        override val female = "Female"
    }

}