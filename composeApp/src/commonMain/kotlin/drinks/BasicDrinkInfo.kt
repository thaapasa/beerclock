package fi.tuska.beerclock.drinks

import fi.tuska.beerclock.images.DrinkImage

interface BasicDrinkInfo {
    /** Name of the drink */
    val name: String

    /** Size of the drink, in cl */
    val quantityCl: Double

    /** Strength of the drink, or alcohol by volume, as a percentage value (range: 0 - 100) */
    val abvPercentage: Double

    /** Image of the drink */
    val image: DrinkImage
}
