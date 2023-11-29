package fi.tuska.beerclock.localization

import androidx.compose.ui.text.intl.Locale
import fi.tuska.beerclock.settings.Gender
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.Month

val strings: Strings = when (Locale.current.language) {
    "fi" -> FiStrings
    "en" -> EnStrings
    else -> EnStrings
}

interface Strings {
    val appName: String

    fun createNumberFormatter(digits: Int): (value: Double) -> String

    fun weekday(day: DayOfWeek): String
    fun weekdayShort(day: DayOfWeek): String
    fun month(month: Month): String
    fun date(day: LocalDate): String
    fun date(day: LocalDateTime): String = date(day.date)
    fun dateShort(day: LocalDate): String
    fun dateShort(day: LocalDateTime): String = dateShort(day.date)
    fun time(time: LocalTime): String
    fun time(time: LocalDateTime): String = time(time.time)

    val pickTime: String
    val pickDate: String
    val dialogOk: String

    fun countryName(countryCode: String): String


    /* Main menu */
    val menu: MenuStrings

    interface MenuStrings {
        val goBack: String
        val menu: String
        val today: String
        val settings: String
        val history: String
        val statistics: String
    }


    /* Drink info */
    val drink: DrinkData

    interface DrinkData {
        val unitLabel: String
        fun units(units: Double): String
        fun abv(abv: Double): String
        fun quantity(quantity: Double): String
        fun itemDescription(quantity: Double, abv: Double): String
        fun drinkTime(time: Instant): String
    }


    /* Error strings */
    val errors: ErrorStrings

    interface ErrorStrings {
        val invalidDecimal: String
    }


    /* Settings screen */
    val settings: SettingsStrings

    interface SettingsStrings {
        val title: String
        val weightLabel: String
        val weightUnit: String
        val weightDescription: String
        val genderLabel: String
        val genderDescription: String
        val startOfDay: String
        val startOfDayDescription: String
        val alcoholGramsLabel: String
        val alcoholGramsUnit: String
        val alcoholGramsDescription: String
        val alcoholGramsByCountry: String
        fun alcoholGramsByCountryOption(countryCode: String): String
        val pickCountry: String
    }


    /* New drink screen */
    val newDrink: NewDrinkStrings

    interface NewDrinkStrings {
        val title: String
        val dateLabel: String
        val timeLabel: String
        val nameLabel: String
        val abvLabel: String
        val abvUnit: String
        val quantityLabel: String
        val quantityUnit: String
        val submit: String
    }


    /* Gender options */
    val gender: GenderStrings

    interface GenderStrings {
        val male: String
        val female: String
    }

    fun forGender(g: Gender): String =
        when (g) {
            Gender.MALE -> gender.male
            Gender.FEMALE -> gender.female
        }


}
