package fi.tuska.beerclock.localization

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month

object FiStrings : Strings {
    override val appName = "Kaljakello"
    override val menu = Menu

    override fun weekdayShort(day: DayOfWeek): String {
        return when (day) {
            DayOfWeek.MONDAY -> "ma"
            DayOfWeek.TUESDAY -> "ti"
            DayOfWeek.WEDNESDAY -> "ke"
            DayOfWeek.THURSDAY -> "to"
            DayOfWeek.FRIDAY -> "pe"
            DayOfWeek.SATURDAY -> "la"
            DayOfWeek.SUNDAY -> "su"
            else -> "?"
        }
    }

    override fun weekday(day: DayOfWeek): String {
        return when (day) {
            DayOfWeek.MONDAY -> "maanantai"
            DayOfWeek.TUESDAY -> "tiistai"
            DayOfWeek.WEDNESDAY -> "keskiviikko"
            DayOfWeek.THURSDAY -> "torstai"
            DayOfWeek.FRIDAY -> "perjantai"
            DayOfWeek.SATURDAY -> "lauantai"
            DayOfWeek.SUNDAY -> "sunnuntai"
            else -> day.toString()
        }
    }

    override fun month(day: Month): String {
        return when (day) {
            Month.JANUARY -> "tammikuu"
            Month.FEBRUARY -> "helmikuu"
            Month.MARCH -> "maaliskuu"
            Month.APRIL -> "huhtikuu"
            Month.MAY -> "toukokuu"
            Month.JUNE -> "kesäkuu"
            Month.JULY -> "heinäkuu"
            Month.AUGUST -> "elokuu"
            Month.SEPTEMBER -> "syyskuu"
            Month.OCTOBER -> "lokakuu"
            Month.NOVEMBER -> "marraskuu"
            Month.DECEMBER -> "joulukuu"
            else -> day.toString()
        }
    }

    override fun date(date: LocalDateTime): String {
        return "${date.dayOfMonth}.${date.monthNumber}."
    }

    object Menu : Strings.MenuStrings {
        override val goBack = "Takaisin"
        override val menu = "Valikko"
        override val today = "Tänään"
        override val settings = "Asetukset"
        override val history = "Historia"
        override val statistics = "Tilastot"
    }

    override val settings = Settings

    object Settings : Strings.SettingsStrings {
        override val title = "Asetukset"
        override val weightLabel = "Paino kiloina"
        override val genderLabel = "Sukupuoli"
    }

    override val gender = Gender

    object Gender : Strings.GenderStrings {
        override val male = "Mies"
        override val female = "Nainen"
    }

    object NewDrinks : Strings.NewDrinkStrings {
        override val title = "Merkkaa juoma"
        override val submit = "Juo!"
    }

    override val newDrink = NewDrinks
}