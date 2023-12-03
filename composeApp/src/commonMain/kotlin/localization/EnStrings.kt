package fi.tuska.beerclock.localization

import fi.tuska.beerclock.drinks.SingleUnitWeights
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
        override val history = "History"
        override val statistics = "Statistics"
    }


    /* Drink info */
    override val drink = DrinkData

    object DrinkData : Strings.DrinkData {
        override val image = "Image of the drink"
        override fun unitLabel(units: Double) = if (unitF(units) == "1") "unit" else "units"
        val unitF = createNumberFormatter(2)
        val abvF = createNumberFormatter(1)
        val quantityF = createNumberFormatter(1)
        override fun abv(abvPercentage: Double) = "${abvF(abvPercentage)} %"
        override fun quantity(quantityCl: Double) = "${quantityF(quantityCl)} cl"
        override fun units(units: Double) = unitF(units)

        override fun drinkTime(time: Instant): String =
            time.toLocalDateTime(TimeZone.currentSystemDefault())
                .let { "${it.hour.zeroPad(2)}:${it.minute.zeroPad(2)}" }

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


    /* Settings screen */
    override val settings = Settings

    object Settings : Strings.SettingsStrings {
        override val title = "Settings"
        override val localeLabel = "Language"
        override val phoneLocale = "Use system locale"
        override val localeDescription = "Select the language for BeerClock."
        override val weightLabel = "Your weight"
        override val weightUnit = "kg"
        override val weightDescription =
            "Your weight affects the calculation formulas. Extra kilos do not affect the burning of alcohol, so enter your own estimate of your ideal weight."
        override val genderLabel = "Your gender"
        override val genderDescription =
            "Your gender affects the calculation formulas. Choose the gender that is closest to your body structure."
        override val startOfDay = "Start of a new day"
        override val startOfDayDescription =
            "When does the new day start? Drinks consumed before this time will be listed under the previous day."
        override val alcoholGramsLabel = "Standard drink"
        override val alcoholGramsUnit = "g/unit"
        override val alcoholGramsDescription =
            "How many grams of alcohol are there in one standard drink (1 unit)? You can also select preset options from the dropdown below."
        override val alcoholGramsByCountry = "Grams by countries"

        override fun alcoholGramsByCountryOption(countryCode: String) =
            "${countryName(countryCode)} (${SingleUnitWeights[countryCode]} g/unit)"

        override val pickCountry = "Select country"
    }


    /* New drink screen */
    override val newDrink = NewDrinks

    object NewDrinks : Strings.NewDrinkStrings {
        override val title = "Add a drink"
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