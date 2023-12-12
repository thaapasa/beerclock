package fi.tuska.beerclock.drinks

import fi.tuska.beerclock.database.BeerDatabase
import fi.tuska.beerclock.database.toDbTime
import fi.tuska.beerclock.images.DrinkImage
import fi.tuska.beerclock.logging.getLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

private val logger = getLogger("DrinkService")

class DrinkService : KoinComponent {

    private val db: BeerDatabase = get()
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

    suspend fun deleteDrinkById(id: Long): Unit {
        withContext(Dispatchers.IO) {
            db.drinkRecordQueries.deleteById(id = id)
        }
        logger.info("Deleted drink $id")
    }

    suspend fun insertDrink(drink: DrinkDetailsFromEditor) {
        withContext(Dispatchers.IO) {
            db.transaction {
                db.drinkRecordQueries.insert(
                    time = drink.time.toDbTime(),
                    name = drink.name,
                    quantityLiters = drink.quantityLiters,
                    abv = drink.abv,
                    image = drink.image.name,
                )
                db.drinkInfoQueries.insert(
                    name = drink.name,
                    quantityLiters = drink.quantityLiters,
                    abv = drink.abv,
                    image = drink.image.name,
                )
            }
        }
    }

    suspend fun updateDrink(id: Long, drink: DrinkDetailsFromEditor) {
        withContext(Dispatchers.IO) {
            db.drinkRecordQueries.update(
                id = id,
                time = drink.time.toDbTime(),
                name = drink.name,
                quantityLiters = drink.quantityLiters,
                abv = drink.abv,
                image = drink.image.name,
            )
        }
    }
}

data class DrinkDetailsFromEditor(
    val name: String,
    val abv: Double,
    val quantityLiters: Double,
    val time: Instant,
    val image: DrinkImage,
)
