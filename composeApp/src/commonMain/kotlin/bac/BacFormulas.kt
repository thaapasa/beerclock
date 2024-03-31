package fi.tuska.beerclock.bac

import fi.tuska.beerclock.settings.UserPreferences
import fi.tuska.beerclock.util.inHours
import kotlinx.datetime.Instant
import kotlin.math.max
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours

/**
 * This class defines how a drink is absorbed into the body. For the given time period, this
 * drink causes an increase of `gramsPerHour` grams of alcohol / hour, into the body. At the
 * end of the period, it is assumed that the entire drink is consumed.
 */
data class AbsorptionSchedule(
    val gramsPerHour: Double,
    val startTime: Instant,
    val endTime: Instant,
)

/**
 * This object defines all the formulas relevant for calculating the blood alcohol concentration
 * and how alcohol is burned from the body. The formulas are collected here for reference.
 *
 * The formulas implement the most up-to-date simple estimation presented in
 * [Wikipedia](https://en.wikipedia.org/wiki/Blood_alcohol_content) as of 2023-12.
 */
object BacFormulas {

    /**
     * Density of alcohol (ethanol), in kg/m3.
     * This is also the weight of one liter of alcohol, in grams.
     */
    const val alcoholDensity = 789.0

    /**
     * Density of water, in kg/m3.
     * This is also the weight of one liter of water, in grams.
     */
    const val waterDensity = 1000.0

    /**
     * Given the selection of how many grams of alcohol are there in a single standard unit:
     * (from user preferences):
     * @return the number of units there are in this drink
     */
    fun getUnitsFromAlcoholWeight(alcoholGrams: Double, prefs: UserPreferences): Double {
        return alcoholGrams / prefs.alchoholGramsInUnit
    }

    fun getUnitsFromDisplayQuantityAbv(
        quantityCl: Double,
        abvPercentage: Double,
        prefs: UserPreferences,
    ): Double {
        val alcoholLiters: Double = quantityCl * abvPercentage / 10000
        val alcoholGrams: Double = alcoholLiters * alcoholDensity
        return alcoholGrams / prefs.alchoholGramsInUnit
    }

    /**
     * @return the amount of alcohol in your blood, for the given amount of alcohol consumed.
     * The result is the amount by volume, expressed per mille ("promillet" in Finnish).
     */
    fun bloodAlcoholConcentration(alcoholGrams: Double, prefs: UserPreferences): Double =
        alcoholGrams / prefs.volumeOfDistribution

    /**
     * Volume of distribution, approximated from body weight and gender multiplier.
     * See [Wikipedia](https://en.wikipedia.org/wiki/Blood_alcohol_content):
     *
     * Vd is the volume of distribution (L);
     * typically body weight (kg) multiplied by 0.71 L/kg for men and 0.58 L/kg for women
     */
    fun volumeOfDistribution(weightKg: Double, genderMultiplier: Double) =
        weightKg * genderMultiplier

    /**
     * Estimate of the rate of alcohol games burned per hour.
     *
     * From [Wikipedia](https://en.wikipedia.org/wiki/Blood_alcohol_content):
     * β is the rate at which alcohol is eliminated (g/L/hr); typically 0.15
     */
    fun alcoholBurnOffRate(volumeOfDistribution: Double) = 0.15 * volumeOfDistribution

    /**
     * Calculate how many grams of alcohol is burnt in the given amount of time.
     */
    fun alcoholBurnedDuring(time: Duration, alcoholBurnOffRate: Double): Double =
        time.inHours() * alcoholBurnOffRate

    /**
     * Calculates the time to burn the given amount of alcohol
     */
    fun timeToBurnAlcohol(alcoholGrams: Double, alcoholBurnOffRate: Double): Duration =
        (alcoholGrams / alcoholBurnOffRate).hours

    /**
     * Calculate how much alcohol is left after a given time, assuming that it is burned with
     * the given burn-off rate.
     */
    fun burnOffAlcohol(
        fromAlcoholGrams: Double,
        time: Duration,
        alcoholBurnOffRate: Double,
    ): Double =
        max(fromAlcoholGrams - alcoholBurnedDuring(time, alcoholBurnOffRate), 0.0)


    /**
     * Quoting from [Alcohol Metabolism](https://www.bgsu.edu/recwell/wellness-connection/alcohol-education/alcohol-metabolism.html):
     * The full effects of a drink are felt within 15 to 45 minutes depending on the speed of absorption.
     *
     * I'm playing it safe here and erring on the side that it's noticeable quicker, so assume
     * 15 minutes = 0.25 hours for one standard drink to be absorbed.
     * I'm using 14 grams for standard drink here, since that's the standard for US.
     */
    val timeToAbsorbAlcoholGram: Duration = (0.25 / 14.0).hours

    val simpleAlcoholAbsorptionRate: Double = 1.0 / timeToAbsorbAlcoholGram.inHours()

    /**
     * Makes a really rough estimate on how long it could take for the drink to be absorbed
     * into your body.
     */
    fun timeToAbsorbGrams(alcoholGrams: Double): Duration {
        return timeToAbsorbAlcoholGram * alcoholGrams
    }

    /**
     * Calculate an estimated schedule on the rate of alcohol absorption for the given amount
     * of alcohol, starting at the given starting time.
     */
    fun absorptionSchedule(alcoholGrams: Double, startTime: Instant): AbsorptionSchedule {
        val duration = timeToAbsorbGrams(alcoholGrams)
        return AbsorptionSchedule(simpleAlcoholAbsorptionRate, startTime, startTime + duration)
    }
}
