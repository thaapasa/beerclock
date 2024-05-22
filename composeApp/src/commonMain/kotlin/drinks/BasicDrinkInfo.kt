package fi.tuska.beerclock.drinks

import fi.tuska.beerclock.bac.BacFormulas
import fi.tuska.beerclock.database.KeyedObject
import fi.tuska.beerclock.images.DrinkImage
import fi.tuska.beerclock.settings.GlobalUserPreferences
import fi.tuska.beerclock.util.CommonParcelable
import fi.tuska.beerclock.util.CommonParcelize
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import kotlin.math.roundToInt
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

@CommonParcelize
open class BasicDrinkInfo(
    /**
     * Identifying key to be used to determine identity and state in lists etc. Must uniquely
     * identify the item and must change whenever the data contents change.
     */
    val key: String,
    /** Name of the drink */
    val name: String,
    /** Producer of the drink (optional) */
    val producer: String = "",
    /** Size of the drink, in cl */
    val quantityCl: Double,
    /** Strength of the drink, or alcohol by volume, as a percentage value (range: 0 - 100) */
    val abvPercentage: Double,
    /** Image of the drink */
    val image: DrinkImage,
    /** Drink rating, as a value from 0 to 5 with a step of 0.5; if given */
    val rating: Double? = null,
    /** Any notes (optional) */
    val note: String? = null,
    /** Categorization of the drink */
    val category: Category? = null,
) : KeyedObject(key), KoinComponent, CommonParcelable {
    protected val prefs: GlobalUserPreferences = get()

    /** Amount of alcohol in the drink, in liters */
    val alcoholLiters: Double =
        BacFormulas.getAlcoholLiters(quantityCl = quantityCl, abvPercentage = abvPercentage)

    /** Amount of alcohol in the drink, in grams */
    val alcoholGrams: Double = BacFormulas.getAlcoholGrams(alcoholLiters = alcoholLiters)

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

    companion object {
        val default = BasicDrinkInfo(
            "default",
            name = "",
            producer = "",
            quantityCl = 33.0,
            abvPercentage = 4.5,
            image = DrinkImage.GENERIC_DRINK,
        )
    }
}

