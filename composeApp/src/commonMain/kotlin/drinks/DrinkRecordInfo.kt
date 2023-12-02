package fi.tuska.beerclock.drinks

import fi.tuska.beerclock.database.DrinkRecord
import fi.tuska.beerclock.database.fromDbTime
import fi.tuska.beerclock.images.DrinkImage
import fi.tuska.beerclock.settings.GlobalUserPreferences
import kotlinx.datetime.Instant
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.math.roundToInt
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

class DrinkRecordInfo(record: DrinkRecord) : KoinComponent {
    private val prefs: GlobalUserPreferences by inject()
    val id = record.id

    /** Name of the drink */
    val name = record.name

    val image: DrinkImage = DrinkImage.forName(record.image)

    /** When was this drink consumed */
    val time = Instant.fromDbTime(record.time)

    /** Size of the drink, in cl */
    val quantityCl = record.quantity_liters * 100.0

    /** Strength of the drink, or alcohol by volume, as a percentage value (range: 0 - 100) */
    val abvPercentage = record.abv * 100

    /** Amount of alcohol in the drink, in liters */
    val alcoholLiters: Double = record.quantity_liters * record.abv

    /** Amount of alcohol in the drink, in grams */
    val alcoholGrams: Double = alcoholLiters * Constants.alcoholDensity

    /**
     * Given the selection of how many grams of alcohol are there in a single standard unit:
     * (from user preferences):
     * @return the number of units there are in this drink
     */
    fun units(): Double {
        return AlcoholCalculator.getUnitsFromAlcoholWeight(alcoholGrams, prefs.prefs)
    }

    /**
     * Calculate the time it takes for your liver to burn off all the alcohol in this drink.
     */
    fun burnOffTime(): Duration {
        val burnRate = AlcoholCalculator.alcoholGramsBurnedPerHour(prefs.prefs)
        val hoursToBurn = alcoholGrams / burnRate
        val minutesToBurn = (hoursToBurn * 60).roundToInt()
        val fullHours = minutesToBurn / 60
        val fullMinutes = minutesToBurn % 60
        return fullHours.hours + fullMinutes.minutes
    }

}
