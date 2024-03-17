package fi.tuska.beerclock.localization

import fi.tuska.beerclock.drinks.Category
import fi.tuska.beerclock.drinks.DrinkInfo
import fi.tuska.beerclock.drinks.DrinkRecordInfo
import fi.tuska.beerclock.drinks.SingleUnitWeights
import fi.tuska.beerclock.screens.statistics.StatisticsMonth
import fi.tuska.beerclock.screens.statistics.StatisticsPeriod
import fi.tuska.beerclock.screens.statistics.StatisticsWeek
import fi.tuska.beerclock.screens.statistics.StatisticsYear
import fi.tuska.beerclock.ui.theme.ThemeSelection
import fi.tuska.beerclock.util.toWeekOfYear
import fi.tuska.beerclock.util.zeroPad
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
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

    override val dec1F = createNumberFormatter(1)
    override val dec2F = createNumberFormatter(2)


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

    override fun month(month: Month): String {
        return month.toString().lowercase().replaceFirstChar { it.titlecase() }
    }

    override fun monthShort(month: Month): String {
        return month(month).substring(0, 3)
    }

    override fun date(date: LocalDate): String {
        return "${dateShort(date)} ${date.year}"
    }

    override fun dateShort(date: LocalDate): String {
        val day = date.dayOfMonth
        val mon = month(date.month).substring(0, 3)
        return "$day $mon"
    }

    override fun time(time: LocalTime): String {
        return "${time.hour.zeroPad(2)}:${time.minute.zeroPad(2)}"
    }

    override fun dateTime(time: LocalDateTime): String {
        return "${date(time.date)} ${time(time.time)}"
    }

    override val pickTime = "Pick time"
    override val pickDate = "Pick date"
    override val dialogOk = "OK"
    override val dialogClose = "Close"
    override val dialogEdit = "Modify"
    override val dialogDelete = "Delete"
    override val remove = "Remove"
    override val cancel = "Cancel"

    override fun countryName(countryCode: String) =
        Country.forIsoCode(countryCode)?.nameEn ?: countryCode

    override fun languageName(locale: AppLocale) = locale.nameEn

    override fun themeName(themeSelection: ThemeSelection) = when (themeSelection) {
        ThemeSelection.SYSTEM -> "Follow system mode"
        ThemeSelection.DARK -> "Dark mode"
        ThemeSelection.LIGHT -> "Light mode"
    }

    /* Main menu */
    override val menu = Menu

    object Menu : Strings.MenuStrings {
        override val goBack = "Go back"
        override val menu = "Menu"
        override val today = "Today"
        override val settings = "Settings"
        override val about = "About the App"
        override val disclosure = "App Usage Disclosure"
        override val drinkLibrary = "Drink Library"
        override val history = "History"
        override val statistics = "Statistics"
    }


    /* Drink info */
    override val drink = DrinkData

    object DrinkData : Strings.DrinkData {
        override val image = "Image of the drink"

        override fun unitLabel(units: Double) =
            if (Strings.get().dec2F(units) == "1") "unit" else "units"

        override fun totalDrinkCount(drinks: Long) =
            "$drinks drink${if (drinks != 1L) "s" else ""}"

        override fun drinkTime(time: Instant): String =
            time.toLocalDateTime(TimeZone.currentSystemDefault())
                .let { "${it.hour.zeroPad(2)}:${it.minute.zeroPad(2)}" }

        override fun categoryName(category: Category?) = category?.nameEn ?: "No category"
        override val categoryLabel = "Category"

        override val timeInfoLabel = "Drinking time"
        override val sizeInfoLabel = "Drink size"
        override val unitsInfoLabel = "Standard units"
        override val alcoholGramsInfoLabel = "Pure alcohol content"
        override val burnOffTimeInfoLabel = "Burn-off time"
        override val totalTimesLabel = "Times drunk"
        override val totalQuantityLabel = "Total quantity"
        override val firstTimeLabel = "Earliest record"
        override val lastTimeLabel = "Latest record"
    }


    /* Error strings */
    override val errors = Errors

    object Errors : Strings.ErrorStrings {
        override val invalidDecimal = "Not a valid decimal value"
    }


    /* Home strings */
    override val home = Home

    object Home : Strings.HomeStrings {
        override val bacPermilles = "BAC ‰"
        override val bacTime = "Time"
        override fun drinkAdded(drink: DrinkRecordInfo) = "Added ${drink.name}"
    }


    /* About page strings */
    override val about = About

    object About : Strings.AboutStrings {
        override val title = "About the app"
        override val appVersion = "App version"
        override val deviceModel = "Device model"
        override val osVersion = "OS version"
        override val sqliteVersion = "SQLite version"
        override val dbVersion = "Database version"
    }


    /* Drink library screen */
    override val library = DrinkLibrary

    object DrinkLibrary : Strings.DrinkLibraryStrings {
        override val title = "Drink library"
        override val newDrinkTitle = "Add new drink"
        override val editDrinkTitle = "Modify drink"
        override val saveDrinkTitle = "Save"
        override val addDefaultDrinks = "Add default drinks"
        override val defaultDrinksAdded = "Default drinks added to library!"
        override fun drinkDeleted(drink: DrinkInfo) = "Deleted ${drink.name}"
    }


    /* Disclosure */
    override val disclosureTexts = arrayOf(
        "BeerClock is an app for calculating current blood alcohol content (BAC) levels, " +
                "and for monitoring your consumption habits over longer periods.",
        "This application is designed exclusively for entertainment purposes, and I cannot " +
                "guarantee that the alcohol quantities it displays correspond to reality. " +
                "You use this app entirely at your own risk. Furthermore, the app should not " +
                "be used competitively or to encourage friends to drink, especially if you " +
                "have already consumed alcohol yourself.",
        "I assume no responsibility for any potential intoxication of users, nor for any " +
                "hangovers or any accidents or mishaps that may occur in connection with " +
                "the use of this application. I am also not liable for the preservation of " +
                "data recorded by the app, as software errors can occur.",
        "This app does not transmit any collected data externally; all information visible " +
                "in the app remains solely on your device (unless you yourself export it and " +
                "transfer it to some other place).",
        "Please understand that the blood alcohol concentration levels displayed by the " +
                "application are based on computational estimates derived from the details " +
                "you enter. Therefore, under no circumstances do I guarantee that you are in " +
                "a condition to drive a vehicle, even if the app might suggest so."
    )

    override val dismissDisclosure = "Understood"


    /* New drink search screen */
    override val newdrink = NewDrinkSearch

    object NewDrinkSearch : Strings.NewDrinkSearchStrings {
        override val searchPlaceholder = "Search for strings"
        override val latestDrinksTitle = "Latest drinks"
        override val emptyLibraryTitle = "No drinks in the library"
        override val emptyLibraryDescription = "Add example drinks by clicking here"
        override fun addNewDrink(name: String) = "New drink: $name"
    }


    /* Settings screen */
    override val settings = Settings

    object Settings : Strings.SettingsStrings {
        override val title = "Settings"
        override val userSettingsTitle = "User"
        override val drinkSettingsTitle = "Drinks"
        override val dataImportTitle = "Import data"
        override fun dateTitle(day: LocalDate) = "${weekdayShort(day.dayOfWeek)}, ${date(day)}"

        override fun weekTitle(day: LocalDate) = "Week ${day.toWeekOfYear().weekNumber}"

        override val localeLabel = "Language"
        override val phoneLocale = "Use system locale"
        override val localeDescription = "Select the language for BeerClock."
        override val themeLabel = "Color scheme"
        override val themeDescription =
            "You can change BeerClock's color scheme here. By default BeerClock will follow your current system mode."
        override val dynamicPaletteLabel = "Dynamic color palette"
        override val dynamicPaletteDescription =
            "Dynamic color palette is automatically generated based on your current background image."
        override val weightLabel = "Your weight"
        override val unitKilogram = "kg"
        override val weightDescription =
            "The amount of water in your body is estimated based on your weight. Extra weight kilos do not affect that amount, so enter your own estimate of your ideal weight."
        override val genderLabel = "Your gender"
        override val genderDescription =
            "Your gender affects the amount of water in your body. Choose the gender that is closest to your body structure."

        override fun burnoffDescription(volume: Double, rate: Double) =
            "According to the data you've entered, your body contains ${dec1F(volume)} liters of water, and you burn of alcohol at a rate of ${
                dec1F(rate)
            } g/h."

        override val startOfDay = "Start of a new day"
        override val startOfDayDescription =
            "When does the new day start? Drinks consumed before this time will be listed under the previous day."
        override val alcoholGramsLabel = "Standard drink"
        override val alcoholGramsUnit = "g/unit"
        override val alcoholGramsDescription =
            "How many grams of alcohol are there in one standard drink (1 unit)? You can also select preset options from the dropdown below."
        override val alcoholGramsByCountry = "Units by countries"
        override val drivingLimitBacLabel = "Driving limit"
        override val unitPermille = "‰"
        override val drivingLimitBacDescription =
            "Maximum blood alcohol concentration for driving in your country. This is 0.5 ‰ in Finland."

        override val maxBacLabel = "Maximum BAC"
        override val maxBacDescription =
            "This determines the BAC ‰ that is shown in the app gauges."
        override val maxDailyUnitsLabel = "Maximum daily units"
        override val maxDailyUnitsDescription =
            "This determines what is the maximum value for daily standard drink units the app gauges show."
        override val maxWeeklyUnitsLabel = "Maximum weekly units"
        override val maxWeeklyUnitsDescription =
            "This determines what is the maximum value for weekly standard drink units the app gauges show."
        override val unitStandardDrinks = "units"

        override fun alcoholGramsByCountryOption(countryCode: String) =
            "${countryName(countryCode)} (${SingleUnitWeights[countryCode]} g/unit)"

        override val pickCountry = "Select country"

        override val importExportTitle = "Create or load backup files"
        override val importExportDescription = listOf(
            "If you wish to delete the app or move it to another phone, you can create a backup file of the BeerClock database.",
            "The backup database will contain all the drink records and your drink library, but your personal preferences from the settings pages will not be included.",
            "To create the backup file, select 'Create backup file', and select the directory where that file will be saved to. BeerClock will then export a backup copy of the app's database.",
            "Move the file to the new phone (you can share it using any file sharing method you like), and then select 'Load from backup', and browse to the file on that phone to load it.",
            "Loading a backup file will erase all data currently in the app database, so proceed at your own risk!"
        )
        override val exportDb = "Create backup"
        override val importDb = "Load backup"

        override fun exportDataMsgComplete(filename: String?, libraryDrinks: Long, records: Long) =
            "Exported $records drink records and $libraryDrinks drinks in library ${if (filename != null) "to $filename" else "succesfully"}"

        override val exportDataMsgError = "There was an error exporting the data, please try again"

        override fun importDataMsgComplete(filename: String?, libraryDrinks: Long, records: Long) =
            "Imported $records drink records and $libraryDrinks drinks from backup file ${if (filename != null) "$filename" else "succesfully"}"

        override val importDataMsgError = "There was an error importing the data, please try again"

        override val importJAlcoMeterTitle = "Import data from jAlcoMeter"
        override val importJAlcoMeterDescriptions = listOf(
            "You can import data from jAlcoMeter into BeerClock. For this you need to first select 'Create backup' from jAlcometer settings, and then locate the created backup file.",
            "jAlcoMeter backups are named 'jAlcoMeter-backup.[date]-[order-number].db', and they are normally created at the folder '/sdcard/jAlcoMeter' on your phone.",
            "Due to Android's new file browsing restrictions, you need to locate the backup file manually and transfer it to e.g. the Downloads folder.",
            "After you have moved the backup file into an accessible location, select 'Import data' from this page and locate the file with the file browser that pops up to start the import process.",
        )
        override val importJAlcoMeterData = "Import data"

        override val importJAlcoMeterMsgInitial = "Click below to start importing data"
        override val importJAlcoMeterMsgStarting = "Starting data import from jAlcoMeter"
        override val importJAlcoMeterMsgImportingLibrary = "Importing drink library..."
        override fun importJAlcoMeterMsgImportingDrink(cur: Long, max: Long) =
            "Importing drink $cur / $max"

        override val importJAlcoMeterMsgComplete = "Data successfully imported from jAlcoMeter"
        override val importJAlcoMeterMsgError =
            "There was an error while importing data from jAlcoMeter"

    }


    /* Drink dialog */
    override val drinkDialog = DrinksDialog

    object DrinksDialog : Strings.DrinkDialogStrings {
        override val createTitle = "Add a drink"
        override val modifyTitle = "Edit drink"
        override val selectImageTitle = "Pick image for the drink"
        override val dateLabel = "Drinking day"
        override val timeLabel = "Time of day"
        override val nameLabel = "Name"
        override val abvLabel = "Alcohol by volume"
        override val abvUnit = "%"
        override val quantityLabel = "Quantity"
        override val quantityUnit = "cl"
        override val submit = "Ready"

        override fun drinkTimeInfo(drinkTime: LocalDateTime) =
            "Drink will be recorded at ${dateTime(drinkTime)}"
    }


    /* Drink history screen */
    override val history = History

    object History : Strings.HistoryStrings {
        override val prevDay = "Previous day"
        override val selectDay = "Pick a day"
        override val nextDay = "Next day"
        override fun drinkAdded(drink: DrinkRecordInfo) = "Added ${drink.name}"
        override fun drinkDeleted(drink: DrinkRecordInfo) = "Deleted ${drink.name}"
    }


    /* Drink statistics screen */
    override val statistics = Statistics

    object Statistics : Strings.StatisticsStrings {
        override val yearTitle = "Year"
        override val monthTitle = "Month"
        override val weekTitle = "Week"
        override fun periodTitle(period: StatisticsPeriod) = when (period) {
            is StatisticsYear -> "Year ${period.year}"
            is StatisticsMonth -> "${month(period.month)} ${period.year}"
            is StatisticsWeek -> "Week ${period.weekOfYear.weekNumber}/${period.weekOfYear.year}"
            else -> "???"
        }

        override val totalsTitle = "All drinks"
        override val unitsLabel = "Units"
        override val dayLabel = "Day"
    }


    /* Gender options */
    override val gender = Gender

    object Gender : Strings.GenderStrings {
        override val male = "Male"
        override val female = "Female"
    }

}
