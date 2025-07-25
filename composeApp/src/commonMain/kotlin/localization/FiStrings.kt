package fi.tuska.beerclock.localization

import fi.tuska.beerclock.drinks.Category
import fi.tuska.beerclock.drinks.DrinkInfo
import fi.tuska.beerclock.drinks.DrinkRecordInfo
import fi.tuska.beerclock.drinks.mix.MixedDrink
import fi.tuska.beerclock.screens.statistics.StatisticsMonth
import fi.tuska.beerclock.screens.statistics.StatisticsPeriod
import fi.tuska.beerclock.screens.statistics.StatisticsWeek
import fi.tuska.beerclock.screens.statistics.StatisticsYear
import fi.tuska.beerclock.ui.components.HelpText
import fi.tuska.beerclock.ui.theme.ThemeSelection
import fi.tuska.beerclock.util.toWeekOfYear
import fi.tuska.beerclock.util.zeroPad
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

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
    override val amountUnit = "×"
    override val amountLabel = "Määrä"

    override fun weekday(day: DayOfWeek): String {
        return when (day) {
            DayOfWeek.MONDAY -> "maanantai"
            DayOfWeek.TUESDAY -> "tiistai"
            DayOfWeek.WEDNESDAY -> "keskiviikko"
            DayOfWeek.THURSDAY -> "torstai"
            DayOfWeek.FRIDAY -> "perjantai"
            DayOfWeek.SATURDAY -> "lauantai"
            DayOfWeek.SUNDAY -> "sunnuntai"
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
        }
    }

    override fun month(month: Month): String {
        return when (month) {
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
        }
    }

    override fun monthShort(month: Month): String {
        return when (month) {
            Month.JANUARY -> "tammi"
            Month.FEBRUARY -> "helmi"
            Month.MARCH -> "maalis"
            Month.APRIL -> "huhti"
            Month.MAY -> "touko"
            Month.JUNE -> "kesä"
            Month.JULY -> "heinä"
            Month.AUGUST -> "elo"
            Month.SEPTEMBER -> "syys"
            Month.OCTOBER -> "loka"
            Month.NOVEMBER -> "marras"
            Month.DECEMBER -> "joulu"
        }
    }

    override fun date(day: LocalDate): String {
        return "${day.day}.${day.month.number}.${day.year}"
    }

    override fun dateShort(day: LocalDate): String {
        return "${day.day}.${day.month.number}."
    }

    override fun time(time: LocalTime): String {
        return "${time.hour}.${time.minute.zeroPad(2)}"
    }

    override fun dateTime(local: LocalDateTime): String {
        return "${date(local.date)} klo ${time(local.time)}"
    }

    override fun relativeTimeToday(time: LocalTime) = "klo ${EnStrings.time(time)}"

    override fun relativeTimeTomorrow(time: LocalTime) = "huomenna klo ${EnStrings.time(time)}"

    override val pickTime = "Valitse kellonaika"
    override val pickDate = "Valitse päivämäärä"
    override val dialogOk = "OK"
    override val dialogClose = "Sulje"
    override val dialogEdit = "Muokkaa"
    override val dialogDelete = "Poista"
    override val remove = "Poista"
    override val cancel = "Peruuta"

    override fun countryName(country: Country) = country.nameFi

    override fun languageName(locale: AppLocale) = locale.nameFi

    override fun themeName(themeSelection: ThemeSelection) = when (themeSelection) {
        ThemeSelection.SYSTEM -> "Järjestelmän tilan mukaan"
        ThemeSelection.DARK -> "Tumma tila"
        ThemeSelection.LIGHT -> "Vaalea tila"
    }


    /* Main menu */
    override val menu = Menu

    object Menu : Strings.MenuStrings {
        override val goBack = "Takaisin"
        override val menu = "Valikko"
        override val today = "Tänään"
        override val settings = "Asetukset"
        override val about = "Tietoja sovelluksesta"
        override val disclosure = "Sovelluksen käyttöehdot"
        override val drinkLibrary = "Juomakirjasto"
        override val history = "Historia"
        override val statistics = "Tilastot"
        override val mixedDrinkCalculator = "Juomasekoitukset"
    }


    /* Drink info */
    override val drink = DrinkData

    object DrinkData : Strings.DrinkData {
        override val image = "Kuva juomasta"
        override fun unitLabel(units: Double) =
            if (Strings.get().dec2F(units) == "1") "annos" else "annosta"

        override fun totalDrinkCount(drinks: Long) =
            "$drinks juoma${if (drinks != 1L) "a" else ""}"

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
        override val totalTimesLabel = "Juontikertoja"
        override val totalQuantityLabel = "Juotu yhteensä"
        override val firstTimeLabel = "Aikaisin kirjaus"
        override val lastTimeLabel = "Viimeisin kirjaus"

        override fun drinkAdded(drink: DrinkRecordInfo) = "Lisätty ${drink.name}"
        override fun drinkDeleted(drink: DrinkRecordInfo) = "Poistettu ${drink.name}"
    }


    /* Error strings */
    override val errors = Errors

    object Errors : Strings.ErrorStrings {
        override val invalidValue = "Virheellinen syöte"
        override val invalidDecimal = "Syötä desimaaliluku"
        override val invalidInteger = "Syötä kokonaisluku"
    }


    /* Home strings */
    override val home = Home

    object Home : Strings.HomeStrings {
        override val bacPermilles = "Promillet ‰"
        override val bacTime = "Aika"
        override val yesterday = HelpText(
            "Keskiyön jälkeen",
            "Nyt on jo huominen, mutta juomat merkataan eiliselle, koska päivän alkamisaika ei ole vielä mennyt (ks. sovelluksen asetukset)."
        )

        override fun cantDrive(soberTime: Instant) = HelpText(
            "Älä aja!",
            "Et ole ajokunnossa juuri nyt! Syöttämäsi tietojen mukaan saatat olla selvin päin ${
                relativeTime(soberTime)
            }."
        )

    }


    /* About page strings */
    override val about = About

    object About : Strings.AboutStrings {
        override val title = "Tietoja sovelluksesta"
        override val appVersion = "Sovelluksen versio"
        override val deviceModel = "Laite"
        override val osVersion = "Järjestelmän versio"
        override val sqliteVersion = "SQLiten versio"
        override val dbVersion = "Tietokannan versio"
        override val copyrightText = "Copyright © 2023-2024 Tuukka Haapasalo"
        override val aboutText =
            arrayOf(
                PlainText("Kaljakello on ilmainen. Voit ladata sen lähdekoodin osoitteesta "),
                LinkText(
                    "https://github.com/thaapasa/beerclock",
                    "https://github.com/thaapasa/beerclock"
                ),
                PlainText(".")
            )
        override val licenseTitle = "Vapaan lähdekoodin lisenssit"
        override val licenseInfo =
            arrayOf(
                PlainText("Tämä sovellus sisältää ohjelmistoja, jotka on lisensoitu avoimen lähdekoodin lisensseillä. Lisätietoja voit lukea osoitteesta "),
                LinkText(
                    "https://github.com/thaapasa/beerclock/blob/main/NOTICE.md",
                    "https://github.com/thaapasa/beerclock/blob/main/NOTICE.md"
                ),
                PlainText(".")
            )
    }


    /* Disclosure */
    override val disclosureTexts = arrayOf(
        "Kaljakello on sovellus, jolla voit seurata promillemäärääsi ja kulutustottumuksiasia " +
                "syöttämällä nauttimasi alkoholijuomat sovellukseen.",
        "Tämä sovellus on suunniteltu yksinomaan viihdekäyttöön, enkä takaa että sen näyttämät " +
                "alkoholimäärät vastaavat todellisuutta. Käytät sovellusta täysin omalla " +
                "vastuullasi. Sovellusta ei pidä käyttää kilpailullisesti tai rohkaista " +
                "ystäviä juomaan, etenkin jos olet itse jo juonut.",
        "En ota vastuuta käyttäjien mahdollisesta päihtymyksestä, krapulasta tai sovelluksen " +
                "käytön yhteydessä mahdollisesti sattuvista tapaturmista tai sekaannuksista. " +
                "En myöskään ole vastuussa sovelluksen tallentamien tietojen säilymisestä, sillä " +
                "ohjelmistovirheet ovat mahdollisia.",
        "Kaljakello ei lähetä kerättyjä tietoja minnekään; kaikki sovelluksessa näkyvät tiedot " +
                "pysyvät ainoastaan laitteessasi (paitsi jos itse varmuuskopioit tiedot ja " +
                "siirrät ne muualle).",
        "Huomioithan, että sovelluksen näyttämät veren alkoholipitoisuudet perustuvat " +
                "laskennallisiin arvioihin, jotka lasketaan syöttämiesi tietojen perusteella. " +
                "Täten en missään nimessä voi taata, että olisit ajokuntoinen, vaikka sovellus " +
                "niin näyttäisikin.",
    )

    override val dismissDisclosure = "Ymmärrän"


    /* Help texts */
    override val help = Help

    object Help : Strings.HelpStrings {
        override val bacStatusGauge = HelpText(
            "Promillet",
            "Tämä mittari näyttää laskennallisen arvion siitä, kuinka monta promillea alkoholia on veressäsi. Laskuissa oletetaan että kaikki juomasi alkoholi olisi imeytynyt heti elimistöösi."
        )
        override val dailyUnitsGauge = HelpText(
            "Päivittäiset annokset",
            "Tämä mittari näyttää kuinka monta annosta alkoholia olet nauttinut tämän juomapäivän aikana."
        )
        override val weeklyUnitsGauge =
            HelpText(
                "Viikoittaiset annokset",
                "Tämä mittari näyttää kuinka monta annosta alkoholia olet nauttinut tämän viikon aikana."
            )
    }


    /* Drink library screen */
    override val library = DrinkLibrary

    object DrinkLibrary : Strings.DrinkLibraryStrings {
        override val title = "Juomakirjasto"
        override val newDrinkTitle = "Lisää juoma"
        override val editDrinkTitle = "Muokkaa juomaa"
        override val saveDrinkTitle = "Tallenna"
        override val addDefaultDrinks = "Lisää esimerkkijuomat"
        override val defaultDrinksAdded = "Esimerkkijuomat lisätty kirjastoon!"
        override fun drinkAdded(drink: DrinkInfo) = "Lisätty ${drink.name}"
        override fun drinkDeleted(drink: DrinkInfo) = "Poistettu ${drink.name}"
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
        override val themeLabel = "Väriteema"
        override val themeDescription =
            "Voit vaihtaa Kaljakellon väriteeman tästä. Oletuksena Kaljakellon väriteema vaihtuu järjestelmän asetusten mukaan."
        override val dynamicPaletteLabel = "Dynaamiset värit"
        override val dynamicPaletteDescription =
            "Tämä valinta luo puhelimesi nykyiseen taustakuvaan sopivan väriteeman automaattisesti."
        override val weightLabel = "Painosi"
        override val unitKilogram = "kg"
        override val weightDescription =
            "Nesteen määrä elimistössäsi arvioidaan painosi mukaan. Ylimääräiset kilot eivät vaikuta nesteen määrään, joten syötä oma arviosi ihannepainostasi."
        override val genderLabel = "Sukupuolesi"
        override val genderDescription =
            "Sukupuolesi vaikuttaa nesteen määrään elimistössäsi. Valitse se sukupuoli joka on lähinnä ruumiinrakennettasi."

        override fun burnoffDescription(volume: Double, rate: Double) =
            "Antamiesi tietojen mukaan ruumiissasi on ${dec1F(volume)} litraa vettä, ja elimistösi polttaa alkoholia ${
                dec1F(rate)
            } g/h."

        override val startOfDay = "Uusi päivä alkaa"
        override val startOfDayDescription =
            "Milloin uusi päivä alkaa. Tätä ennen juodut juomat merkataan edellisen päivän kirjanpitoon."
        override val alcoholGramsLabel = "Alkoholiannos"
        override val alcoholGramsUnit = "g/annos"
        override val alcoholGramsDescription =
            "Kuinka monta grammaa alkoholia on yhdessä annoksessa? Voit myös valita maan mukaan alla olevasta valintalaatikosta."
        override val alcoholGramsByCountry = "Annokset maiden mukaan"
        override val drivingLimitBacLabel = "Autoiluraja"
        override val unitPermille = "‰"
        override val drivingLimitBacDescription =
            "Autoilun promilleraja maassasi. Suomessa tämä on 0,5 ‰."

        override val maxBacLabel = "Promilleraja"
        override val maxBacDescription =
            "Tämä määrittelee mikä promillemäärä on sovelluksen mittareiden maksimi."
        override val maxDailyUnitsLabel = "Päivittäinen annosraja"
        override val maxDailyUnitsDescription =
            "Tämä määrittelee kuinka monta annosta alkoholia on sovelluksen päivittäisten annosten mittareiden maksimi."
        override val maxWeeklyUnitsLabel = "Viikoittainen annosraja"
        override val maxWeeklyUnitsDescription =
            "Tämä määrittelee kuinka monta annosta alkoholia on sovelluksen viikoittaisten annosten mittareiden maksimi."
        override val unitStandardDrinks = "annosta"


        override fun alcoholGramsByCountryOption(country: Country) =
            "${countryName(country)}: ${country.standardUnitWeightGrams} g/annos"

        override val pickCountry = "Valitse maa"

        override val importExportTitle = "Luo tai lataa varmuuskopio"
        override val importExportDescription = listOf(
            "Jos haluat poistaa sovelluksen tai siirtää sen toiseen puhelimeen, voit luoda varmuuskopion Kaljakellon tietokannasta.",
            "Tietokanta sisältää kaikki juomamerkintäsi sekä juomakirjastosi, mutta ei henkilökohtaisia asetuksiasi asetussivuilta.",
            "Luodaksesi varmuuskopion valitse 'Tallenna' ja valitse hakemisto minne haluat varmuuskopion tallentuvan.",
            "Siirrä varmuuskopio haluamallasi tavalla uudelle laitteelle, valitse 'Lataa', ja valitse varmuuskopiotiedosto ladataksesi sen Kaljakelloon.",
            "Varmuuskopion lataaminen poistaa kaikki nykyiset juomatietosi sovelluksesta, joten käytä ominaisuutta omalla vastuullasi!",
            "Huom! Jos lataat vanhemman varmuuskopion Kaljakelloon, sammuta sovellus latauksen jälkeen ja käynnistä se uudelleen, niin Kaljakello päivittää tietokannan automaattisesti uusimpaan versioon."
        )
        override val exportDb = "Tallenna"
        override val importDb = "Lataa"

        override fun exportDataMsgComplete(filename: String?, libraryDrinks: Long, records: Long) =
            "Luotu varmuuskopio${if (filename != null) " tiedostoon $filename" else ""}: $records kirjausta, $libraryDrinks juomaa juomakirjastossa"

        override val exportDataMsgError = "Varmuuskopion luominen epäonnistui, yritä uudelleen"
        override fun importDataMsgComplete(filename: String?, libraryDrinks: Long, records: Long) =
            "Ladattu varmuuskopio${if (filename != null) " tiedostosta $filename" else ""}: $records kirjausta, $libraryDrinks juomaa juomakirjastossa"

        override val importDataMsgError = "Varmuuskopion lataaminen epäonnistui, yritä uudelleen"

        override val importJAlcoMeterTitle = "Tuo tiedot jAlkaMetristä"
        override val importJAlcoMeterDescriptions = listOf(
            "Voit tuoda tiedot Kaljakelloon jAlkaMetristä. Tätä varten sinun täytyy ensin valita jAlkaMetrin asetuksista 'Luo varmuuskopio' ja sen jälkeen paikallistaa luotu varmuuskopiotiedosto.",
            "jAlkaMetrin varmuuskopiot ovat nimeltään 'jAlcoMeter-backup.[päivämäärä]-[järjestysnumero].db', ja ne löytyvät yleensä hakemistosta '/sdcard/jAlcoMeter'.",
            "Johtuen Androidin uusista tiedostonkäyttörajoituksista sinun täytyy etsiä tämä tiedosto käsin ja siirtää se esim. puhelimesi Lataukset -kansioon.",
            "Kun olet siirtänyt tiedoston, valitse 'Tuo tiedot' tältä sivulta ja etsi varmuuskopiotiedosto esiin tulevalla tiedostoselaimella.",
        )
        override val importJAlcoMeterData = "Tuo tiedot"
        override val importJAlcoMeterMsgInitial = "Klikkaa alta aloittaaksesi tietojen latauksen"
        override val importJAlcoMeterMsgStarting = "Tuodaan tietoja jAlkaMetristä..."
        override val importJAlcoMeterMsgImportingLibrary = "Tuodaan juomakirjastoa..."
        override fun importJAlcoMeterMsgImportingDrink(cur: Long, max: Long) =
            "Tuodaan juomaa $cur / $max"

        override val importJAlcoMeterMsgComplete = "Tietojen tuominen jAlkaMetristä onnistui!"
        override val importJAlcoMeterMsgError = "Tietojen tuominen jAlkaMetristä epäonnistui"
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
        override val producerLabel = "Valmistaja"
        override val abvLabel = "Voimakkuus"
        override val abvUnit = "%"
        override val ratingLabel = "Arvostelu"
        override val noteLabel = "Muistiinpanoja"
        override val quantityLabel = "Määrä"
        override val quantityUnit = "cl"
        override val submit = "Valmis"

        override fun drinkTimeInfo(drinkTime: LocalDateTime, inFuture: Boolean) =
            if (inFuture) "Valittu aika on tulevaisuudessa, ole hyvä ja korjaa aika!" else "Ajaksi merkataan ${
                dateTime(drinkTime)
            }"
    }


    /* Drink history screen */
    override val history = History

    object History : Strings.HistoryStrings {
        override val prevDay = "Edellinen päivä"
        override val selectDay = "Valitse päivä"
        override val nextDay = "Seuraava päivä"
    }


    /* Drink statistics screen */
    override val statistics = Statistics

    object Statistics : Strings.StatisticsStrings {
        override val yearTitle = "Vuosi"
        override val monthTitle = "Kuukausi"
        override val weekTitle = "Viikko"
        override fun periodTitle(period: StatisticsPeriod) = when (period) {
            is StatisticsYear -> "Vuosi ${period.year}"
            is StatisticsMonth -> "${month(period.month).replaceFirstChar { it.uppercase() }} ${period.year}"
            is StatisticsWeek -> "Viikko ${period.weekOfYear.weekNumber}/${period.weekOfYear.year}"
            else -> "???"
        }

        override val totalsTitle = "Kaikki juomat"
        override val unitsLabel = "Annoksia"
        override val dayLabel = "Päivä"
    }


    /* Mixed drinks screen */
    override val mixedDrinks = MixedDrinks

    object MixedDrinks : Strings.MixedDrinksStrings {
        override val title: String = "Juomasekoitukset"
        override val itemsTitle = "Ainekset"
        override val instructionsTitle = "Valmistusohjeet"
        override val newMixTitle = "Uusi sekoitus"
        override val editMixTitle = "Muokkaa sekoitusta"
        override val newItemTitle = "Muokkaa ainesosaa"
        override val editItemTitle = "Lisää ainesosa"
        override val save = "Tallenna"
        override val saveToLibrary = "Tee juoma"
        override fun drinkDeleted(mix: MixedDrink) = "Poistettu ${mix.info.name}"
    }


    /* Gender options */
    override val gender = Gender

    object Gender : Strings.GenderStrings {
        override val male = "Mies"
        override val female = "Nainen"
    }

}
