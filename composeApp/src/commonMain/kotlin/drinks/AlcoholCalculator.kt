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
}