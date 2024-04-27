package fi.tuska.beerclock.localization

import androidx.compose.ui.text.intl.Locale
import fi.tuska.beerclock.drinks.Category
import fi.tuska.beerclock.drinks.DrinkInfo
import fi.tuska.beerclock.drinks.DrinkRecordInfo
import fi.tuska.beerclock.drinks.DrinkTimeService
import fi.tuska.beerclock.screens.statistics.StatisticsPeriod
import fi.tuska.beerclock.settings.Gender
import fi.tuska.beerclock.settings.GlobalUserPreferences
import fi.tuska.beerclock.ui.components.HelpText
import fi.tuska.beerclock.ui.theme.ThemeSelection
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.Month
import kotlinx.datetime.plus
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.time.Duration

interface Strings {

    companion object : KoinComponent {

        private val prefs: GlobalUserPreferences by inject()

        fun userLanguage(): String =
            prefs.prefs.locale?.language ?: Locale.current.language

        fun forLanguage(language: String): Strings = when (language) {
            "fi" -> FiStrings
            "en" -> EnStrings
            else -> EnStrings
        }

        fun get(): Strings {
            return forLanguage(userLanguage())
        }
    }

    val appName: String

    fun createNumberFormatter(digits: Int): (value: Double) -> String
    val dec1F: (value: Double) -> String
    val dec2F: (value: Double) -> String

    fun dec1FU(value: Double, unit: String) = "${dec1F(value)} $unit"
    fun dec2FU(value: Double, unit: String) = "${dec2F(value)} $unit"

    fun weekday(day: DayOfWeek): String
    fun weekdayShort(day: DayOfWeek): String
    fun month(month: Month): String
    fun monthShort(month: Month): String
    fun date(day: LocalDate): String
    fun date(day: LocalDateTime): String = date(day.date)
    fun dateShort(day: LocalDate): String
    fun dateShort(day: LocalDateTime): String = dateShort(day.date)
    fun time(time: LocalTime): String
    fun time(time: LocalDateTime): String = time(time.time)
    fun dateTime(local: LocalDateTime): String
    fun relativeTimeToday(time: LocalTime): String = time(time)
    fun relativeTimeTomorrow(time: LocalTime): String

    fun relativeTime(time: Instant): String {
        val times = DrinkTimeService()
        val now = Clock.System.now()
        val today = times.toLocalDateTime(now)
        val targetTime = times.toLocalDateTime(time)
        return when (targetTime.date) {
            today.date -> relativeTimeToday(targetTime.time)
            today.date.plus(DatePeriod(days = 1)) -> relativeTimeTomorrow(targetTime.time)
            else -> dateTime(targetTime)
        }
    }

    val pickTime: String
    val pickDate: String
    val dialogOk: String
    val dialogClose: String
    val dialogEdit: String
    val dialogDelete: String
    val remove: String
    val cancel: String

    fun countryName(country: Country): String
    fun languageName(locale: AppLocale): String
    fun themeName(themeSelection: ThemeSelection): String


    /* Main menu */
    val menu: MenuStrings

    interface MenuStrings {
        val goBack: String
        val menu: String
        val today: String
        val settings: String
        val about: String
        val disclosure: String
        val drinkLibrary: String
        val history: String
        val statistics: String
        val mixedDrinkCalculator: String
    }


    /* Drink info */
    val drink: DrinkData

    interface DrinkData {
        val image: String
        fun unitLabel(units: Double): String
        fun units(units: Double) = get().dec2F(units)
        fun unitsLabeled(units: Double): String {
            val s = get()
            return s.dec2FU(units, s.drink.unitLabel(units))
        }

        fun abv(abvPercentage: Double) = get().dec1FU(abvPercentage, "%")
        fun quantity(quantityCl: Double) = get().dec1FU(quantityCl, "cl")

        fun drinkSize(quantityCl: Double, abvPercentage: Double): String =
            "${quantity(quantityCl)} ${abv(abvPercentage)}"

        fun totalDrinkCount(drinks: Long): String

        fun drinkTime(time: Instant): String

        fun categoryName(category: Category?): String

        val categoryLabel: String
        val timeInfoLabel: String
        val sizeInfoLabel: String
        val unitsInfoLabel: String
        val alcoholGramsInfoLabel: String
        val burnOffTimeInfoLabel: String
        val totalTimesLabel: String
        val totalQuantityLabel: String
        val firstTimeLabel: String
        val lastTimeLabel: String
        fun totalQuantity(quantityLiters: Double): String {
            val s = get()
            return if (quantityLiters < 1.0) s.dec1FU(quantityLiters * 100.0, "cl")
            else s.dec1FU(quantityLiters, "l")
        }

        fun unitsInfo(units: Double) = units(units)
        fun sizeInfo(quantityCl: Double, abvPercentage: Double) =
            drinkSize(quantityCl, abvPercentage)

        fun alcoholAmountInfo(grams: Double, liters: Double) =
            "${get().dec2F(liters * 100)} cl = ${get().dec1F(grams)} g"

        fun burnOffTimeInfo(time: Duration) = time.toString()

        fun drinkAdded(drink: DrinkRecordInfo): String
        fun drinkDeleted(drink: DrinkRecordInfo): String
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
        val yesterday: HelpText
        fun cantDrive(soberTime: Instant): HelpText
    }


    /* About screen */
    val about: AboutStrings

    interface AboutStrings {
        val title: String
        val appVersion: String
        val deviceModel: String
        val osVersion: String
        val sqliteVersion: String
        val dbVersion: String
        val copyrightText: String
        val aboutText: TextContent
        val licenseTitle: String
        val licenseInfo: TextContent
    }


    /* Disclosure */
    val disclosureTexts: Array<String>
    val dismissDisclosure: String


    /* Help texts */
    val help: HelpStrings

    interface HelpStrings {
        val bacStatusGauge: HelpText
        val dailyUnitsGauge: HelpText
        val weeklyUnitsGauge: HelpText
    }


    /* Drink library screen */
    val library: DrinkLibraryStrings

    interface DrinkLibraryStrings {
        val title: String
        val newDrinkTitle: String
        val editDrinkTitle: String
        val saveDrinkTitle: String
        val addDefaultDrinks: String
        val defaultDrinksAdded: String
        fun drinkDeleted(drink: DrinkInfo): String
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
        val userSettingsTitle: String
        val drinkSettingsTitle: String
        val dataImportTitle: String
        fun dateTitle(day: LocalDate): String
        fun weekTitle(day: LocalDate): String
        val localeLabel: String
        val phoneLocale: String
        val localeDescription: String
        val themeLabel: String
        val themeDescription: String
        val dynamicPaletteLabel: String
        val dynamicPaletteDescription: String
        val weightLabel: String
        val unitKilogram: String
        val weightDescription: String
        val genderLabel: String
        val genderDescription: String
        fun burnoffDescription(volume: Double, rate: Double): String
        val startOfDay: String
        val startOfDayDescription: String
        val alcoholGramsLabel: String
        val alcoholGramsUnit: String
        val alcoholGramsDescription: String
        val alcoholGramsByCountry: String
        val drivingLimitBacLabel: String
        val drivingLimitBacDescription: String
        val unitPermille: String
        val maxBacLabel: String
        val maxBacDescription: String
        val maxDailyUnitsLabel: String
        val maxDailyUnitsDescription: String
        val maxWeeklyUnitsLabel: String
        val maxWeeklyUnitsDescription: String
        val unitStandardDrinks: String
        fun alcoholGramsByCountryOption(country: Country): String
        val pickCountry: String

        val importExportTitle: String
        val importExportDescription: List<String>
        val exportDb: String
        val importDb: String
        fun exportDataMsgComplete(filename: String?, libraryDrinks: Long, records: Long): String
        val exportDataMsgError: String
        fun importDataMsgComplete(filename: String?, libraryDrinks: Long, records: Long): String
        val importDataMsgError: String

        val importJAlcoMeterTitle: String
        val importJAlcoMeterDescriptions: List<String>
        val importJAlcoMeterData: String
        val importJAlcoMeterMsgInitial: String
        val importJAlcoMeterMsgStarting: String
        val importJAlcoMeterMsgImportingLibrary: String
        fun importJAlcoMeterMsgImportingDrink(cur: Long, max: Long): String
        val importJAlcoMeterMsgComplete: String
        val importJAlcoMeterMsgError: String
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
        val producerLabel: String
        val abvLabel: String
        val abvUnit: String
        val ratingLabel: String
        val noteLabel: String
        val quantityLabel: String
        val quantityUnit: String
        val submit: String
        fun drinkTimeInfo(drinkTime: LocalDateTime, inFuture: Boolean): String
    }


    /* Drink history screen */
    val history: HistoryStrings

    interface HistoryStrings {
        val prevDay: String
        val selectDay: String
        val nextDay: String
    }


    /* Drink statistics screen */
    val statistics: StatisticsStrings

    interface StatisticsStrings {
        val yearTitle: String
        val monthTitle: String
        fun monthValue(year: Int, month: Month) =
            get().month(month).replaceFirstChar { it.uppercase() }

        val weekTitle: String
        fun weekValue(year: Int, weekNumber: Int) = weekNumber.toString()
        fun periodTitle(period: StatisticsPeriod): String
        val totalsTitle: String
        val unitsLabel: String
        val dayLabel: String
    }


    /* Mixed drinks screen */
    val mixedDrinks: MixedDrinksStrings

    interface MixedDrinksStrings {
        val title: String
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
