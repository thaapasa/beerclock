package fi.tuska.beerclock.localization

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

object FiStrings : Strings {
    override val appName = "Kaljakello"

    override fun createNumberFormatter(digits: Int) = getDecimalFormatter(
        maximumFractionDigits = digits,
        decimalSeparator = ',',
        isGroupingUsed = false,
        groupingSeparator = ' '
    )

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

    
    /* Main menu */
    override val menu = Menu

    object Menu : Strings.MenuStrings {
        override val goBack = "Takaisin"
        override val menu = "Valikko"
        override val today = "Tänään"
        override val settings = "Asetukset"
        override val history = "Historia"
        override val statistics = "Tilastot"
    }


    /* Drink info */
    override val drink = DrinkData

    object DrinkData : Strings.DrinkData {
        override val unitLabel = "annosta"
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
                .let { "klo ${it.hour}:${it.minute}" }
    }


    /* Settings screen */
    override val settings = Settings

    object Settings : Strings.SettingsStrings {
        override val title = "Asetukset"
        override val weightLabel = "Paino kiloina"
        override val genderLabel = "Sukupuoli"
    }


    /* New drink screen */
    override val newDrink = NewDrinks

    object NewDrinks : Strings.NewDrinkStrings {
        override val title = "Merkkaa juoma"
        override val submit = "Juo!"
    }


    /* Gender options */
    override val gender = Gender

    object Gender : Strings.GenderStrings {
        override val male = "Mies"
        override val female = "Nainen"
    }

}
