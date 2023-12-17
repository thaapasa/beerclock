package fi.tuska.beerclock.drinks

import fi.tuska.beerclock.images.DrinkImage

interface BasicDrinkInfo {
    /**
     * Identifying key to be used to determine identity in lists etc. Could be a
     * database index column, or some other uniquely identifying field.
     */
    val key: Any

    /** Name of the drink */
    val name: String

    /** Size of the drink, in cl */
    val quantityCl: Double

    /** Strength of the drink, or alcohol by volume, as a percentage value (range: 0 - 100) */
    val abvPercentage: Double

    /** Image of the drink */
    val image: DrinkImage?

    /** How many standard units this drink contains, as per user preferences */
    fun units(): Double
}
