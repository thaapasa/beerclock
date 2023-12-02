package fi.tuska.beerclock.drinks

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
     * @return the amount of alcohol burned (grams / hour).
     */
    fun alcoholGramsBurnedPerHour(prefs: UserPreferences): Double {
        // The most commonly used estimate is that your body burns alcohol 1 g for each 10 kg
        // of your weight per hour.
        return prefs.weightKg / 10.0
    }

}