package fi.tuska.beerclock.screens.newdrink

import fi.tuska.beerclock.database.BeerDatabase
import fi.tuska.beerclock.database.toDbTime
import fi.tuska.beerclock.images.DrinkImage
import kotlinx.datetime.Clock

data class DrinkDef(
    val name: String,
    val quantityLiters: Double,
    val abv: Double, // Alcohol by volume in percentage
    val image: DrinkImage
)

val DrinkLibrary = listOf(
    DrinkDef("Large brewski", 0.5, 4.6, DrinkImage.BEER_GLASS_1),
    DrinkDef("Can'o'beer", 0.33, 4.6, DrinkImage.BEER_CAN1),
    DrinkDef("Red wine", 0.16, 13.0, DrinkImage.RED_WINE_GLASS1),
    DrinkDef("White wine", 0.12, 13.0, DrinkImage.WHITE_WINE_GLASS1),
    DrinkDef("Champagne", 0.12, 12.0, DrinkImage.CHAMPAGNE_GLASS1),
    DrinkDef("Martini", 0.1, 32.5, DrinkImage.MARTINI),
    DrinkDef("Whisky", 0.04, 43.0, DrinkImage.WHISKY1)
)


fun addNewDrink(db: BeerDatabase) {
    val now = Clock.System.now()
    val drink = DrinkLibrary.random()
    db.drinkRecordQueries.insert(
        now.toDbTime(),
        name = drink.name,
        quantity_liters = drink.quantityLiters,
        abv = drink.abv,
        image = drink.image.name
    )
}