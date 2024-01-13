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

object FiStrings : Strings {
    override val appName = "Kaljakello"

    override fun createNumberFormatter(digits: Int) = getDecimalFormatter(
        maximumFractionDigits = digits,
        decimalSeparator = ',',
        isGroupingUsed = false,
        groupingSeparator = ' '
    )

    override val dec1F = createNumberFormatter(1)
    override val dec2F = createNumberFormatter(2)

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

    override fun date(date: LocalDate): String {
        return "${date.dayOfMonth}.${date.monthNumber}.${date.year}"
    }

    override fun dateShort(date: LocalDate): String {
        return "${date.dayOfMonth}.${date.monthNumber}."
    }

    override fun time(time: LocalTime): String {
        return "${time.hour}.${time.minute.zeroPad(2)}"
    }

    override fun dateTime(time: LocalDateTime): String {
        return "${date(time.date)} klo ${time(time.time)}"
    }

    override val pickTime = "Valitse kellonaika"
    override val pickDate = "Valitse päivämäärä"
    override val dialogOk = "OK"
    override val dialogClose = "Sulje"
    override val dialogEdit = "Muokkaa"
    override val dialogDelete = "Poista"

    override fun countryName(countryCode: String) =
        Country.forIsoCode(countryCode)?.nameFi ?: countryCode

    override fun languageName(locale: AppLocale) = locale.nameFi

    /* Main menu */
    override val menu = Menu

    object Menu : Strings.MenuStrings {
        override val goBack = "Takaisin"
        override val menu = "Valikko"
        override val today = "Tänään"
        override val settings = "Asetukset"
        override val about = "Tietoja sovelluksesta"
        override val drinkLibrary = "Juomakirjasto"
        override val history = "Historia"
        override val statistics = "Tilastot"
    }


    /* Drink info */
    override val drink = DrinkData

    object DrinkData : Strings.DrinkData {
        override val image = "Kuva juomasta"
        override fun unitLabel(units: Double) =
            if (Strings.get().dec2F(units) == "1") "annos" else "annosta"

        override fun drinkTime(time: Instant): String =
            time.toLocalDateTime(TimeZone.currentSystemDefault())
                .let { "klo ${it.hour}:${it.minute.zeroPad(2)}" }

        override fun categoryName(category: Category?) = category?.nameFi ?: "Ei kategoriaa"

        override val categoryLabel = "Kategoria"
        override val timeInfoLabel = "Joit tämän"
        override val sizeInfoLabel = "Juoman koko"
        override val unitsInfoLabel = "Annoksia"
        override val alcoholGramsInfoLabel = "Puhdasta alkoholia"
        override val burnOffTimeInfoLabel = "Alkoholin palamisaika"
    }


    /* Error strings */
    override val errors = Errors

    object Errors : Strings.ErrorStrings {
        override val invalidDecimal = "Syötä desimaaliluku"
    }


    /* Home strings */
    override val home = Home

    object Home : Strings.HomeStrings {
        override val bacPermilles = "Promillet ‰"
        override val bacTime = "Aika"
    }


    /* About page strings */
    override val about = About

    object About : Strings.AboutStrings {
        override val title = "Tietoja sovelluksesta"
        override val appVersion = "Sovelluksen versio"
        override val deviceModel = "Laite"
        override val osVersion = "Järjestelmän versio"
        override val sqliteVersion = "SQLiten versio"
        override val dbVersion = "Tietokannan version"
    }


    /* Drink library screen */
    override val library = DrinkLibrary

    object DrinkLibrary : Strings.DrinkLibraryStrings {
        override val title = "Juomakirjasto"
        override val newDrinkTitle = "Lisää juoma"
        override val editDrinkTitle = "Muokkaa juomaa"
        override val saveDrinkTitle = "Tallenna"
    }


    /* New drink search screen */
    override val newdrink = NewDrinkSearch

    object NewDrinkSearch : Strings.NewDrinkSearchStrings {
        override val searchPlaceholder = "Hae juomia"
        override val latestDrinksTitle = "Viimeisimmät juomat"
        override val emptyLibraryTitle = "Juomakirjasto on tyhjä"
        override val emptyLibraryDescription = "Lisää esimerkkijuomat klikkaamalla tästä"
        override fun addNewDrink(name: String) = "Uusi juoma: $name"
    }


    /* Settings screen */
    override val settings = Settings

    object Settings : Strings.SettingsStrings {
        override val title = "Asetukset"
        override val userSettingsTitle = "Käyttäjä"
        override val drinkSettingsTitle = "Juomat"
        override val dataImportTitle = "Tuo tiedot"

        override fun dateTitle(day: LocalDate) =
            "${weekdayShort(day.dayOfWeek).replaceFirstChar { it.uppercase() }} ${date(day)}"

        override fun weekTitle(day: LocalDate) = "Viikko ${day.toWeekOfYear().weekNumber}"

        override val localeLabel = "Kieli"
        override val phoneLocale = "Puhelimen kielen mukaan"
        override val localeDescription = "Valitse millä kielellä haluat käyttää Kaljakelloa."
        override val weightLabel = "Painosi"
        override val weightUnit = "kg"
        override val weightDescription =
            "Nesteen määrä elimistössäsi arvioidaan painosi mukaan. Ylimääräiset kilot eivät vaikuta nesteen määrään, joten syötä oma arviosi ihannepainostasi."
        override val genderLabel = "Sukupuolesi"
        override val genderDescription =
            "Sukupuolesi vaikuttaa nesteen määrään elimistössäsi. Valitse se sukupuoli joka on lähinnä ruumiinrakennettasi."
        override val startOfDay = "Uusi päivä alkaa"
        override val startOfDayDescription =
            "Milloin uusi päivä alkaa. Tätä ennen juodut juomat merkataan edellisen päivän kirjanpitoon."
        override val alcoholGramsLabel = "Alkoholiannos"
        override val alcoholGramsUnit = "g/annos"
        override val alcoholGramsDescription =
            "Kuinka monta grammaa alkoholia on yhdessä annoksessa? Voit myös valita maan mukaan alla olevasta valintalaatikosta."
        override val alcoholGramsByCountry = "Annokset maiden mukaan"
        override val drivingLimitBacLabel = "Autoiluraja"
        override val drivingLimitBacUnit = "‰"
        override val drivingLimitBacDescription =
            "Autoilun promilleraja maassasi. Suomessa tämä on 0,5 ‰."

        override fun alcoholGramsByCountryOption(countryCode: String) =
            "${countryName(countryCode)}: ${SingleUnitWeights[countryCode]} g/annos"

        override val pickCountry = "Valitse maa"

        override val importJAlcoMeterTitle = "Tuo tiedot jAlkaMetristä"
        override val importJAlcoMeterDescriptions = listOf(
            "Voit tuoda tiedot Kaljakelloon jAlkaMetristä. Valitse ensin jAlkaMetrin asetuksista 'Luo varmuuskopio', ja sen jälkeen 'Tuo tiedot' tältä sivulta.",
            "jAlkaMetrin varmuuskopiot ovat nimeltään 'jAlcoMeter-backup.[päivämäärä]-[järjestysnumero].db'. Valitse listasta se varmuuskopio jonka haluat ladata.",
            "Jos haluat siirtää varmuuskopion uuteen laitteeseen, sinun täytyy siirtää jAlkaMetrin varmuuskopiotiedosto itse uudelle laitteelle. Varmuuskopiot löytyvät hakemistosta /sdcard/jAlcoMeter. Siirrä kyseinen hakemisto tälle laitteelle niin Kaljakello löytää varmuuskopiot."
        )
        override val importJAlcoMeterData = "Tuo tiedot"

    }


    /* Drink dialog */
    override val drinkDialog = DrinksDialog

    object DrinksDialog : Strings.DrinkDialogStrings {
        override val createTitle = "Merkkaa juoma"
        override val modifyTitle = "Muokkaa juomaa"
        override val selectImageTitle = "Valitse kuva juomalle"
        override val dateLabel = "Juomapäivä"
        override val timeLabel = "Kellonaika"
        override val nameLabel = "Nimi"
        override val abvLabel = "Voimakkuus"
        override val abvUnit = "%"
        override val quantityLabel = "Määrä"
        override val quantityUnit = "cl"
        override val submit = "Valmis"

        override fun drinkTimeInfo(drinkTime: LocalDateTime) =
            "Ajaksi merkataan ${dateTime(drinkTime)}"
    }


    /* Drink history screen */
    override val history = History

    object History : Strings.HistoryStrings {
        override val prevDay = "Edellinen päivä"
        override val selectDay = "Valitse päivä"
        override val nextDay = "Seuraava päivä"
    }


    /* Gender options */
    override val gender = Gender

    object Gender : Strings.GenderStrings {
        override val male = "Mies"
        override val female = "Nainen"
    }


}
