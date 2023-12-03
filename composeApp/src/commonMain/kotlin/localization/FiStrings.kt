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

    private val Countries = mapOf(
        "AT" to "Itävalta",
        "AU" to "Australia",
        "CA" to "Kanada",
        "DK" to "Tanska",
        "ES" to "Espanja",
        "FI" to "Suomi",
        "FR" to "Ranska",
        "GB" to "Iso-Britannia",
        "HU" to "Unkari",
        "IE" to "Irlanti",
        "IS" to "Islanti",
        "IT" to "Italia",
        "JP" to "Japani",
        "NL" to "Alankomaat",
        "NZ" to "Uusi-Seelanti",
        "PL" to "Puola",
        "PT" to "Portugali",
        "SE" to "Ruotsi",
        "US" to "Yhdysvallat",
    )

    override fun countryName(countryCode: String) = Countries[countryCode] ?: countryCode


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
        override val image = "Kuva juomasta"
        override fun unitLabel(units: Double) = if (unitF(units) == "1") "annos" else "annosta"
        val unitF = createNumberFormatter(2)
        val abvF = createNumberFormatter(1)
        val gramsF = createNumberFormatter(2)
        val quantityF = createNumberFormatter(1)
        override fun abv(abvPercentage: Double) = "${abvF(abvPercentage)} %"
        override fun quantity(quantityCl: Double) = "${quantityF(quantityCl)} cl"
        override fun units(units: Double) = unitF(units)

        override fun drinkTime(time: Instant): String =
            time.toLocalDateTime(TimeZone.currentSystemDefault())
                .let { "klo ${it.hour}:${it.minute.zeroPad(2)}" }

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


    /* Settings screen */
    override val settings = Settings

    object Settings : Strings.SettingsStrings {
        override val title = "Asetukset"
        override val localeLabel = "Kieli"
        override val phoneLocale = "Puhelimen kielen mukaan"
        override val localeDescription = "Valitse millä kielellä haluat käyttää Kaljakelloa."
        override val weightLabel = "Painosi"
        override val weightUnit = "kg"
        override val weightDescription =
            "Painosi vaikuttaa laskentakaavoihin. Ylimääräiset kilot eivät vaikuta alkoholin palamiseen, joten syötä oma arviosi ihannepainostasi."
        override val genderLabel = "Sukupuolesi"
        override val genderDescription =
            "Sukupuolesi vaikuttaa laskentakaavioihin. Valitse se sukupuoli joka on lähinnä ruumiinrakennettasi."
        override val startOfDay = "Uusi päivä alkaa"
        override val startOfDayDescription =
            "Milloin uusi päivä alkaa. Tätä ennen juodut juomat merkataan edellisen päivän kirjanpitoon."
        override val alcoholGramsLabel = "Alkoholiannos"
        override val alcoholGramsUnit = "g/annos"
        override val alcoholGramsDescription =
            "Kuinka monta grammaa alkoholia on yhdessä annoksessa? Voit myös valita maan mukaan alla olevasta valintalaatikosta."
        override val alcoholGramsByCountry = "Painot maiden mukaan"

        override fun alcoholGramsByCountryOption(countryCode: String) =
            "${countryName(countryCode)}: ${SingleUnitWeights[countryCode]} g/annos"

        override val pickCountry = "Valitse maa"
    }


    /* New drink screen */
    override val newDrink = NewDrinks

    object NewDrinks : Strings.NewDrinkStrings {
        override val title = "Merkkaa juoma"
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
