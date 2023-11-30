package fi.tuska.beerclock.drinks

import fi.tuska.beerclock.database.BeerDatabase
import fi.tuska.beerclock.database.toDbTime
import fi.tuska.beerclock.images.DrinkImage
import fi.tuska.beerclock.logging.getLogger
import fi.tuska.beerclock.util.ZeroHour
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

private val logger = getLogger("DrinkService")

class DrinkService : KoinComponent {

    private val db: BeerDatabase = get()

    suspend fun getDrinksForDay(date: LocalDate): List<DrinkRecordInfo> {
        val zone = TimeZone.currentSystemDefault()
        val startTime = LocalDateTime(date = date, time = ZeroHour).toInstant(zone)
        val endTime = startTime.plus(1, DateTimeUnit.DAY, zone)
        val drinks = withContext(Dispatchers.IO) {
            db.drinkRecordQueries.selectByTime(
                startTime.toDbTime(),
                endTime.toDbTime()
            ).executeAsList()
        }
        logger.info("Found ${drinks.size} drinks for $date")
        return drinks.map(::DrinkRecordInfo)
    }

    suspend fun getDrinksForToday(): List<DrinkRecordInfo> {
        val zone = TimeZone.currentSystemDefault()
        return getDrinksForDay(Clock.System.now().toLocalDateTime(zone).date)
    }

    suspend fun deleteDrinkById(id: Long): Unit {
        withContext(Dispatchers.IO) {
            db.drinkRecordQueries.deleteById(id)
        }
        logger.info("Deleted drink $id")
    }

    suspend fun insertDrink(drink: NewDrinkRecord) {
        withContext(Dispatchers.IO) {
            db.drinkRecordQueries.insert(
                time = drink.time.toDbTime(),
                name = drink.name,
                quantity_liters = drink.quantityLiters,
                abv = drink.abv,
                image = drink.image.name
            )
        }
    }
}

data class NewDrinkRecord(
    val name: String,
    val abv: Double,
    val quantityLiters: Double,
    val time: Instant,
    val image: DrinkImage
)
