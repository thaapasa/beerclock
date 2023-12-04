package fi.tuska.beerclock.bac

import fi.tuska.beerclock.settings.UserPreferences

object AlcoholCalculator {

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
        val alcoholGrams: Double = alcoholLiters * Constants.alcoholDensity
        return alcoholGrams / prefs.alchoholGramsInUnit
    }


    /**
     * Calculates alcohol burn rate, based on updatd Widmark formula from
     * [Wikipedia](https://en.wikipedia.org/wiki/Blood_alcohol_content).
     *
     * @return the amount of alcohol burned (grams / hour).
     */
    fun alcoholGramsBurnedPerHour(prefs: UserPreferences): Double {
        // β is the rate at which alcohol is eliminated (g/L/hr); typically 0.15
        return 0.15 * prefs.volumeOfDistribution
    }

    /**
     * @return the amount of alcohol in your blood, for the given amount of alcohol consumed.
     * The result is the amount by volume, expressed per mille ("promillet" in Finnish).
     */
    fun bloodAlcoholConcentration(alcoholGrams: Double, prefs: UserPreferences): Double =
        alcoholGrams / prefs.volumeOfDistribution
}
