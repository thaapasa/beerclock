package fi.tuska.beerclock.drinks

import fi.tuska.beerclock.images.DrinkImage
import fi.tuska.beerclock.localization.Strings

data class DrinkDef(
    val name: String,
    val quantityLiters: Double,
    val abvPercentage: Double, // Alcohol by volume in percentage
    val image: DrinkImage
)

val ExampleDrinksEn = listOf(
    DrinkDef("Large brewski", 0.5, 4.6, DrinkImage.BEER_GLASS_1),
    DrinkDef("Can'o'beer", 0.33, 4.6, DrinkImage.BEER_CAN_1),
    DrinkDef("Red wine", 0.16, 13.0, DrinkImage.RED_WINE_GLASS_1),
    DrinkDef("White wine", 0.12, 13.0, DrinkImage.WHITE_WINE_GLASS_1),
    DrinkDef("Martini", 0.1, 32.5, DrinkImage.MARTINI_1),
    DrinkDef("Cognac", 0.04, 43.0, DrinkImage.COGNAC_1)
)

val ExampleDrinksFi = listOf(
    DrinkDef("Iso olut", 0.5, 4.6, DrinkImage.BEER_GLASS_1),
    DrinkDef("Keppana", 0.33, 4.6, DrinkImage.BEER_CAN_1),
    DrinkDef("Punkku", 0.16, 13.0, DrinkImage.RED_WINE_GLASS_1),
    DrinkDef("Valkkari", 0.12, 13.0, DrinkImage.WHITE_WINE_GLASS_1),
    DrinkDef("Martini", 0.1, 32.5, DrinkImage.MARTINI_1),
    DrinkDef("Konjakki", 0.04, 43.0, DrinkImage.COGNAC_1)
)

fun exampleDrinks() = when (Strings.userLanguage()) {
    "fi" -> ExampleDrinksFi
    else -> ExampleDrinksEn
}

