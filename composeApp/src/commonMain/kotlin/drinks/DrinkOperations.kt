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
            producer = drink.producer,
            name = drink.name,
            category = drink.category?.name,
            quantityLiters = drink.quantityLiters,
            abv = drink.abv,
            image = drink.image.name,
            rating = drink.rating,
            note = drink.note,
        )
        val rowId = db.drinkRecordQueries.lastInsertedId().executeAsOne()
        db.drinkLibraryQueries.recordDrunk(
            name = drink.name,
            producer = drink.producer,
            category = drink.category?.name,
            quantityLiters = drink.quantityLiters,
            abv = drink.abv,
            image = drink.image.name,
            rating = drink.rating,
            note = drink.note,
        )
        return DrinkRecordInfo.fromRecord(db.drinkRecordQueries.selectById(rowId).executeAsOne())
    }

    fun importDrink(importId: Long, drink: DrinkDetailsFromEditor) {
        db.drinkRecordQueries.import(
            importId = importId,
            producer = drink.producer,
            time = drink.time.toDbTime(),
            name = drink.name,
            category = drink.category?.name,
            quantityLiters = drink.quantityLiters,
            abv = drink.abv,
            image = drink.image.name,
            rating = drink.rating,
            note = drink.note,
        )
    }

    fun insertDrinkInfo(drink: DrinkDetailsFromEditor): DrinkInfo {
        db.drinkLibraryQueries.insert(
            name = drink.name,
            producer = drink.producer,
            category = drink.category?.name,
            quantityLiters = drink.quantityLiters,
            abv = drink.abv,
            image = drink.image.name,
            rating = drink.rating,
            note = drink.note,
        )
        val rowId = db.drinkLibraryQueries.lastInsertedId().executeAsOne()
        return DrinkInfo.fromRecord(db.drinkLibraryQueries.selectById(rowId).executeAsOne())
    }

    fun getDrinkLibrary(): List<DrinkInfo> {
        return db.drinkLibraryQueries.selectAll().executeAsList().map(DrinkInfo::fromRecord)
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
