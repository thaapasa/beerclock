package fi.tuska.beerclock.localization

import androidx.compose.ui.text.intl.Locale
import fi.tuska.beerclock.drinks.Category
import fi.tuska.beerclock.settings.Gender
import fi.tuska.beerclock.settings.GlobalUserPreferences
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.Month
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.time.Duration

interface Strings {

    companion object : KoinComponent {

        private val prefs: GlobalUserPreferences by inject()

        fun userLanguage(): String =
            prefs.prefs.locale?.language ?: Locale.current.language

        inline fun forLanguage(language: String): Strings = when (language) {
            "fi" -> FiStrings
            "en" -> EnStrings
            else -> EnStrings
        }

        inline fun get(): Strings {
            return forLanguage(userLanguage())
        }
    }

    val appName: String

    fun createNumberFormatter(digits: Int): (value: Double) -> String
    val dec1F: (value: Double) -> String
    val dec2F: (value: Double) -> String

    fun weekday(day: DayOfWeek): String
    fun weekdayShort(day: DayOfWeek): String
    fun month(month: Month): String
    fun date(day: LocalDate): String
    fun date(day: LocalDateTime): String = date(day.date)
    fun dateShort(day: LocalDate): String
    fun dateShort(day: LocalDateTime): String = dateShort(day.date)
    fun time(time: LocalTime): String
    fun time(time: LocalDateTime): String = time(time.time)
    fun dateTime(local: LocalDateTime): String

    val pickTime: String
    val pickDate: String
    val dialogOk: String
    val dialogClose: String
    val dialogEdit: String
    val dialogDelete: String

    fun countryName(countryCode: String): String
    fun languageName(locale: AppLocale): String


    /* Main menu */
    val menu: MenuStrings

    interface MenuStrings {
        val goBack: String
        val menu: String
        val today: String
        val settings: String
        val drinkLibrary: String
        val history: String
        val statistics: String
    }


    /* Drink info */
    val drink: DrinkData

    interface DrinkData {
        val image: String
        fun unitLabel(units: Double): String
        fun units(units: Double) = get().dec2F(units)
        fun abv(abvPercentage: Double) = "${get().dec1F(abvPercentage)} %"
        fun quantity(quantityCl: Double) = "${get().dec1F(quantityCl)} cl"

        fun drinkSize(quantityCl: Double, abvPercentage: Double): String =
            "${quantity(quantityCl)} ${abv(abvPercentage)}"

        fun drinkTime(time: Instant): String

        fun categoryName(category: Category?): String

        val categoryLabel: String
        val timeInfoLabel: String
        val sizeInfoLabel: String
        val unitsInfoLabel: String
        val alcoholGramsInfoLabel: String
        val burnOffTimeInfoLabel: String
        fun unitsInfo(units: Double) = units(units)
        fun sizeInfo(quantityCl: Double, abvPercentage: Double) =
            drinkSize(quantityCl, abvPercentage)

        fun alcoholAmountInfo(grams: Double, liters: Double) =
            "${get().dec2F(liters * 100)} cl = ${get().dec1F(grams)} g"

        fun burnOffTimeInfo(time: Duration) = time.toString()
    }


    /* Error strings */
    val errors: ErrorStrings

    interface ErrorStrings {
        val invalidDecimal: String
    }


    /* Home screen */
    val home: HomeStrings

    interface HomeStrings {
        val bacPermilles: String
        val bacTime: String
    }


    /* Drink library screen */
    val library: DrinkLibraryStrings

    interface DrinkLibraryStrings {
        val title: String
        val newDrinkTitle: String
        val editDrinkTitle: String
        val saveDrinkTitle: String
    }


    /* New drink search screen */
    val newdrink: NewDrinkSearchStrings

    interface NewDrinkSearchStrings {
        val searchPlaceholder: String
        val latestDrinksTitle: String
        val emptyLibraryTitle: String
        val emptyLibraryDescription: String
        fun addNewDrink(name: String): String
    }


    /* Settings screen */
    val settings: SettingsStrings

    interface SettingsStrings {
        val title: String
        fun dateTitle(date: LocalDate): String
        fun weekTitle(date: LocalDate): String
        val localeLabel: String
        val phoneLocale: String
        val localeDescription: String
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
        val drivingLimitBacLabel: String
        val drivingLimitBacUnit: String
        val drivingLimitBacDescription: String
        fun alcoholGramsByCountryOption(countryCode: String): String
        val pickCountry: String
    }


    /* Drink dialog strings */
    val drinkDialog: DrinkDialogStrings

    interface DrinkDialogStrings {
        val createTitle: String
        val modifyTitle: String
        val selectImageTitle: String
        val dateLabel: String
        val timeLabel: String
        val nameLabel: String
        val abvLabel: String
        val abvUnit: String
        val quantityLabel: String
        val quantityUnit: String
        val submit: String
        fun drinkTimeInfo(drinkTime: LocalDateTime): String
    }


    /* Drink history screen */
    val history: HistoryStrings

    interface HistoryStrings {
        val prevDay: String
        val selectDay: String
        val nextDay: String
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
