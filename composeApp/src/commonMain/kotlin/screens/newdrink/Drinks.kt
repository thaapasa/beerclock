package fi.tuska.beerclock.screens.newdrink

import fi.tuska.beerclock.database.BeerDatabase
import fi.tuska.beerclock.images.DrinkImage
import fi.tuska.beerclock.screens.today.drinks

fun addNewDrink(db: BeerDatabase) {
    db.drinksQueries.insert(
        1321,
        drinks.random(),
        DrinkImage.values().random().path
    )
}