package fi.tuska.beerclock.drinks

import app.cash.sqldelight.Query
import fi.tuska.beerclock.database.BeerDatabase
import fi.tuska.beerclock.database.toDbTime
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class DrinkOperations(private val db: BeerDatabase) {
    fun insertDrink(drink: DrinkDetailsFromEditor): DrinkRecordInfo {
        db.drinkRecordQueries.insert(
            time = drink.time.toDbTime(),
            name = drink.name,
            category = drink.category?.name,
            quantityLiters = drink.quantityLiters,
            abv = drink.abv,
            image = drink.image.name,
        )
        val rowId = db.drinkRecordQueries.lastInsertedId().executeAsOne()
        db.drinkLibraryQueries.recordDrunk(
            name = drink.name,
            category = drink.category?.name,
            quantityLiters = drink.quantityLiters,
            abv = drink.abv,
            image = drink.image.name,
        )
        return DrinkRecordInfo(db.drinkRecordQueries.selectById(rowId).executeAsOne())
    }

    fun importDrink(importId: Long, drink: DrinkDetailsFromEditor) {
        db.drinkRecordQueries.import(
            importId = importId,
            time = drink.time.toDbTime(),
            name = drink.name,
            category = drink.category?.name,
            quantityLiters = drink.quantityLiters,
            abv = drink.abv,
            image = drink.image.name,
        )
    }

    fun insertDrinkInfo(drink: DrinkDetailsFromEditor) {
        db.drinkLibraryQueries.insert(
            name = drink.name,
            category = drink.category?.name,
            quantityLiters = drink.quantityLiters,
            abv = drink.abv,
            image = drink.image.name,
        )
    }

    fun getDrinkLibrary(): List<DrinkInfo> {
        return db.drinkLibraryQueries.selectAll().executeAsList().map(::DrinkInfo)
    }
}

fun <T : Any> Query<T>.asFlow(): Flow<List<T>> = callbackFlow {
    val listener = Query.Listener {
        val items = executeAsList()
        trySend(items)
    }
    addListener(listener)
    val items = executeAsList()
    trySend(items)
    awaitClose {
        removeListener(listener)
    }
}

fun <T : Any> Query<T>.asRowFlow(): Flow<T> = callbackFlow {
    val listener = Query.Listener {
        val row = executeAsOne()
        trySend(row)
    }
    addListener(listener)
    val row = executeAsOne()
    trySend(row)
    awaitClose {
        removeListener(listener)
    }
}
