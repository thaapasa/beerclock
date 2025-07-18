package fi.tuska.beerclock.drinks

import fi.tuska.beerclock.database.BeerDatabase
import fi.tuska.beerclock.database.fromDbTime
import fi.tuska.beerclock.database.toDbTime
import fi.tuska.beerclock.logging.getLogger
import fi.tuska.beerclock.screens.statistics.StatisticsPeriod
import fi.tuska.beerclock.settings.UserPreferences
import fi.tuska.beerclock.wear.WearSyncService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import kotlin.time.Instant

private val logger = getLogger("DrinkService")

class DrinkService : KoinComponent {

    val db: BeerDatabase = get()
    private val times = DrinkTimeService()
    private val wearSync = WearSyncService(this)

    suspend fun getDrinksForDay(date: LocalDate): List<DrinkRecordInfo> {
        val range = times.dayTimeRange(date)
        val drinks = withContext(Dispatchers.IO) {
            db.drinkRecordQueries.selectByTime(
                startTime = range.start.toDbTime(),
                endTime = range.end.toDbTime(),
            ).executeAsList()
        }
        logger.info("Found ${drinks.size} drinks for $date")
        return drinks.map(DrinkRecordInfo::fromRecord).reversed()
    }

    fun flowDrinksForDay(date: LocalDate): Flow<List<DrinkRecordInfo>> {
        val range = times.dayTimeRange(date)
        return db.drinkRecordQueries.selectByTime(
            startTime = range.start.toDbTime(),
            endTime = range.end.toDbTime(),
        ).asFlow().map { it.map(DrinkRecordInfo::fromRecord).reversed() }.flowOn(Dispatchers.IO)
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
        return drinks.map(DrinkRecordInfo::fromRecord)
    }

    suspend fun getUnitsForWeek(today: LocalDate, prefs: UserPreferences): Double {
        val range = times.currentWeekRange(today)
        val units = withContext(Dispatchers.IO) {
            db.drinkRecordQueries.countUnitsByTime(
                multiplier = prefs.alcoholAbvLitersToUnitMultiplier,
                startTime = range.start.toDbTime(),
                endTime = range.end.toDbTime(),
            ).executeAsOne()
        }
        return units.SUM ?: 0.0;
    }

    fun flowUnitsForWeek(today: LocalDate, prefs: UserPreferences): Flow<Double> {
        val range = times.currentWeekRange(today)
        return db.drinkRecordQueries.countUnitsByTime(
            multiplier = prefs.alcoholAbvLitersToUnitMultiplier,
            startTime = range.start.toDbTime(),
            endTime = range.end.toDbTime(),
        ).asRowFlow().map { it.SUM ?: 0.0 }
    }

    suspend fun getLatestDrinks(limit: Long): List<BasicDrinkInfo> {
        val drinks = withContext(Dispatchers.IO) {
            db.drinkRecordQueries.selectLatestDrinks(
                limit = limit
            ).executeAsList()
        }
        return drinks.map(LatestDrinkInfo::fromRecord)
    }

    fun flowMatchingDrinksByName(name: String, limit: Long): Flow<List<DrinkInfo>> {
        val drinks = db.drinkLibraryQueries.findMatchingByName(name, limit).asFlow()
        return drinks.map { it.map(DrinkInfo::fromRecord) }.flowOn(Dispatchers.IO)
    }

    fun flowDrinksForCategories(category: Category?): Flow<List<DrinkInfo>> {
        val flow = db.drinkLibraryQueries.selectAllByCategory(category?.toString()).asFlow()
        return flow.map { it.map(DrinkInfo::fromRecord) }.flowOn(Dispatchers.IO)
    }

    suspend fun flowDrinkDetails(drink: BasicDrinkInfo?): Flow<DrinkDetails?> {
        if (drink == null) {
            return flow { emit(null) }
        }
        return db.drinkRecordQueries.queryDetails(drink.producer, drink.name).asRowFlow().map {
            DrinkDetails(
                timesDrunk = it.count,
                quantityLiters = it.quantityLiters ?: 0.0,
                firstTimeDrunk = it.minTime?.let(Instant::fromDbTime),
                lastTimeDrunk = it.maxTime?.let(Instant::fromDbTime),
            )
        }
    }

    suspend fun flowDrinkNotes(drink: BasicDrinkInfo?): Flow<List<DrinkNote>> {
        if (drink == null) {
            return flow { emit(emptyList()) }
        }
        return db.drinkRecordQueries.getNotesAndRatingsForDrink(drink.producer, drink.name).asFlow()
            .map { list ->
                list.map {
                    DrinkNote(
                        time = Instant.fromDbTime(it.time),
                        note = it.note,
                        rating = it.rating,
                    )
                }
            }
    }

    suspend fun libraryHasDrinks(): Boolean {
        val result = withContext(Dispatchers.IO) {
            db.drinkLibraryQueries.hasDrinks().executeAsList()
        }
        return result.isNotEmpty()
    }


    suspend fun getDrinkById(id: Long): DrinkRecordInfo {
        return withContext(Dispatchers.IO) {
            val d = db.drinkRecordQueries.selectById(id = id).executeAsOne()
            return@withContext DrinkRecordInfo.fromRecord(d)
        }
    }

    suspend fun deleteDrinkById(id: Long): Unit {
        withContext(Dispatchers.IO) {
            db.drinkRecordQueries.deleteById(id = id)
        }
        logger.info("Deleted drink record $id")
        wearSync.sendStatusToWatch()
    }

    suspend fun deleteDrinkInfoById(id: Long): Unit {
        withContext(Dispatchers.IO) {
            db.drinkLibraryQueries.deleteById(id = id)
        }
        logger.info("Deleted drink library entry $id")
    }

    suspend fun insertDrink(drink: DrinkDetailsFromEditor): DrinkRecordInfo {
        return withContext(Dispatchers.IO) {
            val res = db.transactionWithResult {
                operations.insertDrink(drink)
            }
            wearSync.sendStatusToWatch()
            res
        }
    }


    suspend fun insertDrinkInfo(drink: DrinkDetailsFromEditor): DrinkInfo {
        return withContext(Dispatchers.IO) {
            return@withContext operations.insertDrinkInfo(drink)
        }
    }

    suspend fun updateDrinkRecord(id: Long, drink: DrinkDetailsFromEditor): DrinkRecordInfo {
        return withContext(Dispatchers.IO) {
            db.drinkRecordQueries.update(
                id = id,
                time = drink.time.toDbTime(),
                name = drink.name,
                producer = drink.producer,
                category = drink.category?.name,
                quantityLiters = drink.quantityLiters,
                abv = drink.abv,
                image = drink.image.name,
                rating = drink.rating,
                note = drink.note,
            )
            wearSync.sendStatusToWatch()

            return@withContext getDrinkById(id)
        }
    }

    suspend fun updateDrinkInfo(id: Long, drink: DrinkDetailsFromEditor): DrinkInfo {
        return withContext(Dispatchers.IO) {
            db.drinkLibraryQueries.update(
                id = id,
                name = drink.name,
                producer = drink.producer,
                category = drink.category?.name,
                quantityLiters = drink.quantityLiters,
                abv = drink.abv,
                image = drink.image.name,
                rating = drink.rating,
                note = drink.note,
            )
            return@withContext DrinkInfo.fromRecord(
                db.drinkLibraryQueries.selectById(id).executeAsOne()
            )
        }
    }

    suspend fun addExampleDrinks() {
        withContext(Dispatchers.IO) {
            db.transaction {
                exampleDrinks().forEach {
                    db.drinkLibraryQueries.insert(
                        name = it.name,
                        producer = it.producer,
                        category = it.category?.name,
                        quantityLiters = it.quantityCl / 100.0,
                        abv = it.abvPercentage / 100.0,
                        image = it.image.name,
                        rating = it.rating,
                        note = it.note,
                    )
                }
            }
        }
    }

    suspend fun getStatisticsByCategory(
        period: StatisticsPeriod,
        prefs: UserPreferences,
    ): StatisticsByCategory {
        val range = period.range
        return withContext(Dispatchers.IO) {
            val list = db.transactionWithResult {
                db.statisticsQueries.getStatisticsByCategory(
                    multiplier = prefs.alcoholAbvLitersToUnitMultiplier,
                    startTime = range.start.toDbTime(),
                    endTime = range.end.toDbTime()
                ).executeAsList().map(CategoryStatistics::fromDb)
            }
            StatisticsByCategory(list, period, prefs)
        }
    }

    suspend fun getDrinkUnitsForPeriod(
        period: StatisticsPeriod,
        prefs: UserPreferences,
    ): List<DrinkUnitInfo> {
        val range = period.range
        return withContext(Dispatchers.IO) {
            return@withContext db.transactionWithResult {
                db.statisticsQueries.getDrinkUnitInfoForPeriod(
                    multiplier = prefs.alcoholAbvLitersToUnitMultiplier,
                    startTime = range.start.toDbTime(),
                    endTime = range.end.toDbTime()
                ).executeAsList().map(DrinkUnitInfo::fromDb)
            }
        }
    }

    val operations = DrinkOperations(db)
}
