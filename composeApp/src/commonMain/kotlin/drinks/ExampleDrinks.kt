package fi.tuska.beerclock.drinks

import fi.tuska.beerclock.images.DrinkImage
import fi.tuska.beerclock.localization.Strings

data class DrinkDef(
    override val name: String,
    override val quantityCl: Double,
    override val abvPercentage: Double, // Alcohol by volume in percentage
    override val image: DrinkImage
) : BasicDrinkInfo

val ExampleDrinksEn = listOf(
    DrinkDef("Large brewski", 50.0, 4.6, DrinkImage.BEER_GLASS_1),
    DrinkDef("Can'o'beer", 33.0, 4.6, DrinkImage.BEER_CAN_1),
    DrinkDef("Red wine", 16.0, 13.0, DrinkImage.RED_WINE_GLASS_1),
    DrinkDef("White wine", 12.0, 13.0, DrinkImage.WHITE_WINE_GLASS_1),
    DrinkDef("Martini", 10.0, 32.5, DrinkImage.MARTINI_1),
    DrinkDef("Cognac", 4.0, 43.0, DrinkImage.COGNAC_1)
)

val ExampleDrinksFi = listOf(
    DrinkDef("Iso olut", 50.0, 4.6, DrinkImage.BEER_GLASS_1),
    DrinkDef("Keppana", 33.0, 4.6, DrinkImage.BEER_CAN_1),
    DrinkDef("Punkku", 16.0, 13.0, DrinkImage.RED_WINE_GLASS_1),
    DrinkDef("Valkkari", 12.0, 13.0, DrinkImage.WHITE_WINE_GLASS_1),
    DrinkDef("Martini", 10.0, 32.5, DrinkImage.MARTINI_1),
    DrinkDef("Konjakki", 4.0, 43.0, DrinkImage.COGNAC_1)
)

fun exampleDrinks() = when (Strings.userLanguage()) {
    "fi" -> ExampleDrinksFi
    else -> ExampleDrinksEn
}
