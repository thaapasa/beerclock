package fi.tuska.beerclock.drinks

import fi.tuska.beerclock.images.DrinkImage

data class DrinkDef(
    val name: String,
    val quantityLiters: Double,
    val abvPercentage: Double, // Alcohol by volume in percentage
    val image: DrinkImage
)

val ExampleDrinks = listOf(
    DrinkDef("Large brewski", 0.5, 4.6, DrinkImage.BEER_GLASS_1),
    DrinkDef("Can'o'beer", 0.33, 4.6, DrinkImage.BEER_CAN_1),
    DrinkDef("Red wine", 0.16, 13.0, DrinkImage.RED_WINE_GLASS_1),
    DrinkDef("White wine", 0.12, 13.0, DrinkImage.WHITE_WINE_GLASS_1),
    DrinkDef("Martini", 0.1, 32.5, DrinkImage.MARTINI_1),
    DrinkDef("Cognac", 0.04, 43.0, DrinkImage.COGNAC_1)
)
