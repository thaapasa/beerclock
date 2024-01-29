package fi.tuska.beerclock.localization

import fi.tuska.beerclock.drinks.Category
import fi.tuska.beerclock.drinks.SingleUnitWeights
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

    override fun month(day: Month): String {
        return day.toString().lowercase().replaceFirstChar { it.titlecase() }
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

    override fun countryName(countryCode: String) =
        Country.forIsoCode(countryCode)?.nameEn ?: countryCode

    override fun languageName(locale: AppLocale) = locale.nameEn

    /* Main menu */
    override val menu = Menu

    object Menu : Strings.MenuStrings {
        override val goBack = "Go back"
        override val menu = "Menu"
        override val today = "Today"
        override val settings = "Settings"
        override val about = "About the app"
        override val drinkLibrary = "Drink library"
        override val history = "History"
        override val statistics = "Statistics"
    }


    /* Drink info */
    override val drink = DrinkData

    object DrinkData : Strings.DrinkData {
        override val image = "Image of the drink"

        override fun unitLabel(units: Double) =
            if (Strings.get().dec2F(units) == "1") "unit" else "units"

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
    }


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
        override val weightLabel = "Your weight"
        override val weightUnit = "kg"
        override val weightDescription =
            "The amount of water in your body is estimated based on your weight. Extra weight kilos do not affect that amount, so enter your own estimate of your ideal weight."
        override val genderLabel = "Your gender"
        override val genderDescription =
            "Your gender affects the amount of water in your body. Choose the gender that is closest to your body structure."
        override val startOfDay = "Start of a new day"
        override val startOfDayDescription =
            "When does the new day start? Drinks consumed before this time will be listed under the previous day."
        override val alcoholGramsLabel = "Standard drink"
        override val alcoholGramsUnit = "g/unit"
        override val alcoholGramsDescription =
            "How many grams of alcohol are there in one standard drink (1 unit)? You can also select preset options from the dropdown below."
        override val alcoholGramsByCountry = "Units by countries"
        override val drivingLimitBacLabel = "Driving limit"
        override val drivingLimitBacUnit = "‰"
        override val drivingLimitBacDescription =
            "Maximum blood alcohol concentration for driving in your country. This is 0.5 ‰ in Finland."

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
    }


    /* Gender options */
    override val gender = Gender

    object Gender : Strings.GenderStrings {
        override val male = "Male"
        override val female = "Female"
    }

}
