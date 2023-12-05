package fi.tuska.beerclock.bac

import fi.tuska.beerclock.settings.UserPreferences

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
    inline fun getUnitsFromAlcoholWeight(alcoholGrams: Double, prefs: UserPreferences): Double {
        return alcoholGrams / prefs.alchoholGramsInUnit
    }

    inline fun getUnitsFromDisplayQuantityAbv(
        quantityCl: Double,
        abvPercentage: Double,
        prefs: UserPreferences
    ): Double {
        val alcoholLiters: Double = quantityCl * abvPercentage / 10000
        val alcoholGrams: Double = alcoholLiters * BacFormulas.alcoholDensity
        return alcoholGrams / prefs.alchoholGramsInUnit
    }

    /**
     * @return the amount of alcohol in your blood, for the given amount of alcohol consumed.
     * The result is the amount by volume, expressed per mille ("promillet" in Finnish).
     */
    inline fun bloodAlcoholConcentration(alcoholGrams: Double, prefs: UserPreferences): Double =
        alcoholGrams / prefs.volumeOfDistribution

    /**
     * Volume of distribution, approximated from body weight and gender multiplier.
     * See [Wikipedia](https://en.wikipedia.org/wiki/Blood_alcohol_content):
     *
     * Vd is the volume of distribution (L);
     * typically body weight (kg) multiplied by 0.71 L/kg for men and 0.58 L/kg for women
     */
    inline fun volumeOfDistribution(weightKg: Double, genderMultiplier: Double) =
        weightKg * genderMultiplier


    /**
     * Estimate of the rate of alcohol games burned per hour.
     *
     * From [Wikipedia](https://en.wikipedia.org/wiki/Blood_alcohol_content):
     * β is the rate at which alcohol is eliminated (g/L/hr); typically 0.15
     */
    inline fun alcoholBurnOffRate(volumeOfDistribution: Double) = 0.15 * volumeOfDistribution

}
