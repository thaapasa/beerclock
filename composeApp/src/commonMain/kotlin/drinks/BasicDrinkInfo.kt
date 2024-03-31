package fi.tuska.beerclock.drinks

import fi.tuska.beerclock.bac.BacFormulas
import fi.tuska.beerclock.images.Image
import fi.tuska.beerclock.settings.GlobalUserPreferences
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import kotlin.math.roundToInt
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

abstract class BasicDrinkInfo(
    /** Name of the drink */
    val name: String,
    /** Producer of the drink (optional) */
    val producer: String? = null,
    /** Size of the drink, in cl */
    val quantityCl: Double,
    /** Strength of the drink, or alcohol by volume, as a percentage value (range: 0 - 100) */
    val abvPercentage: Double,
    /** Image of the drink */
    val image: Image,
    /** Any notes (optional) */
    val note: String? = null,
    /** Categorization of the drink */
    val category: Category? = null,
) : KoinComponent {
    protected val prefs: GlobalUserPreferences = get()

    /**
     * Identifying key to be used to determine identity in lists etc. Could be a
     * database index column, or some other uniquely identifying field.
     */
    abstract val key: Any

    /** Amount of alcohol in the drink, in liters */
    val alcoholLiters: Double = quantityCl * abvPercentage / 10_000.0

    /** Amount of alcohol in the drink, in grams */
    val alcoholGrams: Double = alcoholLiters * BacFormulas.alcoholDensity

    /**
     * Given the selection of how many grams of alcohol are there in a single standard unit:
     * (from user preferences):
     * @return the number of units there are in this drink
     */
    fun units() = BacFormulas.getUnitsFromAlcoholWeight(alcoholGrams, prefs.prefs)

    /**
     * Calculate the time it takes for your liver to burn off all the alcohol in this drink.
     */
    fun burnOffTime(): Duration {
        val burnRate = prefs.prefs.alcoholBurnOffRate
        val hoursToBurn = alcoholGrams / burnRate
        val minutesToBurn = (hoursToBurn * 60).roundToInt()
        val fullHours = minutesToBurn / 60
        val fullMinutes = minutesToBurn % 60
        return fullHours.hours + fullMinutes.minutes
    }
}
