package fi.tuska.beerclock.drinks

import app.cash.sqldelight.Query
import fi.tuska.beerclock.database.BeerDatabase
import fi.tuska.beerclock.database.toDbTime
import fi.tuska.beerclock.images.DrinkImage
import fi.tuska.beerclock.logging.getLogger
import fi.tuska.beerclock.settings.UserPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

private val logger = getLogger("DrinkService")

class DrinkService : KoinComponent {

    val db: BeerDatabase = get()
    private val times = DrinkTimeService()

    suspend fun getDrinksForDay(date: LocalDate): List<DrinkRecordInfo> {
        val range = times.dayTimeRange(date)
        val drinks = withContext(Dispatchers.IO) {
            db.drinkRecordQueries.selectByTime(
                startTime = range.start.toDbTime(),
                endTime = range.end.toDbTime(),
            ).executeAsList()
        }
        logger.info("Found ${drinks.size} drinks for $date")
        return drinks.map(::DrinkRecordInfo)
    }

    suspend fun getDrinksForHomeScreen(today: LocalDate): List<DrinkRecordInfo> {
        val yesterday = today.minus(1, DateTimeUnit.DAY)
        val range = times.dayTimeRange(yesterday, today)
        val drinks = withContext(Dispatchers.IO) {
            db.drinkRecordQueries.selectByTime(
                startTime = range.start.toDbTime(),
                endTime = range.end.toDbTime(),
            ).executeAsList()
        }
        logger.info("Found ${drinks.size} drinks for $yesterday - $today")
        return drinks.map(::DrinkRecordInfo)
    }

    suspend fun getUnitsForWeek(today: LocalDate, prefs: UserPreferences): Double {
        val yesterday = today.minus(1, DateTimeUnit.DAY)
        val range = times.currentWeekRange(today)
        val units = withContext(Dispatchers.IO) {
            db.drinkRecordQueries.countUnitsByTime(
                multiplier = prefs.alcoholAbvLitersToUnitMultiplier,
                startTime = range.start.toDbTime(),
                endTime = range.end.toDbTime(),
            ).executeAsOne()
        }
        logger.info("FOO $units");
        return units.SUM ?: 0.0;
    }

    suspend fun getLatestDrinks(limit: Long): List<LatestDrinkInfo> {
        val drinks = withContext(Dispatchers.IO) {
            db.drinkRecordQueries.selectLatestDrinks(
                limit = limit
            ).executeAsList()
        }
        return drinks.map(::LatestDrinkInfo)
    }

    fun flowMatchingDrinksByName(name: String, limit: Long): Flow<List<DrinkInfo>> {
        val drinks = db.drinkLibraryQueries.findMatchingByName(name, limit).asFlow()
        return drinks.map { it.map(::DrinkInfo) }.flowOn(Dispatchers.IO)
    }

    fun flowDrinksForCategories(categories: Set<Category>): Flow<List<DrinkInfo>> {
        val flow = if (categories.isEmpty()) db.drinkLibraryQueries.selectAll().asFlow()
        else db.drinkLibraryQueries.selectAllByCategory(categories.map { it.toString() })
            .asFlow()
        return flow.map { it.map(::DrinkInfo) }.flowOn(Dispatchers.IO)
    }

    suspend fun libraryHasDrinks(): Boolean {
        val result = withContext(Dispatchers.IO) {
            db.drinkLibraryQueries.hasDrinks().executeAsList()
        }
        return result.isNotEmpty()
    }

    suspend fun deleteDrinkById(id: Long): Unit {
        withContext(Dispatchers.IO) {
            db.drinkRecordQueries.deleteById(id = id)
        }
        logger.info("Deleted drink $id")
    }

    suspend fun deleteDrinkInfoById(id: Long): Unit {
        withContext(Dispatchers.IO) {
            db.drinkLibraryQueries.deleteById(id = id)
        }
        logger.info("Deleted drink $id")
    }

    suspend fun insertDrink(drink: DrinkDetailsFromEditor) {
        withContext(Dispatchers.IO) {
            db.transaction {
                operations.insertDrink(drink)
            }
        }
    }

    suspend fun insertDrinkInfo(drink: DrinkDetailsFromEditor) {
        withContext(Dispatchers.IO) {
            operations.insertDrinkInfo(drink)
        }
    }

    suspend fun updateDrinkRecord(id: Long, drink: DrinkDetailsFromEditor) {
        withContext(Dispatchers.IO) {
            db.drinkRecordQueries.update(
                id = id,
                time = drink.time.toDbTime(),
                name = drink.name,
                category = drink.category?.name,
                quantityLiters = drink.quantityLiters,
                abv = drink.abv,
                image = drink.image.name,
            )
        }
    }

    suspend fun updateDrinkInfo(id: Long, drink: DrinkDetailsFromEditor) {
        withContext(Dispatchers.IO) {
            db.drinkLibraryQueries.update(
                id = id,
                name = drink.name,
                category = drink.category?.name,
                quantityLiters = drink.quantityLiters,
                abv = drink.abv,
                image = drink.image.name,
            )
        }
    }

    suspend fun addExampleDrinks() {
        withContext(Dispatchers.IO) {
            db.transaction {
                exampleDrinks().forEach {
                    db.drinkLibraryQueries.insert(
                        name = it.name,
                        category = it.category?.name,
                        quantityLiters = it.quantityCl / 100.0,
                        abv = it.abvPercentage / 100.0,
                        image = it.image.name,
                    )
                }
            }
        }
    }

    val operations = DrinkOperations(db)
}

class DrinkOperations(private val db: BeerDatabase) {
    fun insertDrink(drink: DrinkDetailsFromEditor) {
        db.drinkRecordQueries.insert(
            time = drink.time.toDbTime(),
            name = drink.name,
            category = drink.category?.name,
            quantityLiters = drink.quantityLiters,
            abv = drink.abv,
            image = drink.image.name,
        )
        db.drinkLibraryQueries.recordDrunk(
            name = drink.name,
            category = drink.category?.name,
            quantityLiters = drink.quantityLiters,
            abv = drink.abv,
            image = drink.image.name,
        )
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
        db.drinkLibraryQueries.importDrunk(
            name = drink.name,
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

data class DrinkDetailsFromEditor(
    val name: String,
    val category: Category?,
    val abv: Double,
    val quantityLiters: Double,
    val time: Instant,
    val image: DrinkImage,
)

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
