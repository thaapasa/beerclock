package fi.tuska.beerclock.drinks

import fi.tuska.beerclock.database.BeerDatabase
import fi.tuska.beerclock.database.DrinkRecord
import fi.tuska.beerclock.database.toDbTime
import fi.tuska.beerclock.logging.getLogger
import fi.tuska.beerclock.screens.newdrink.addNewDrink
import fi.tuska.beerclock.util.ZeroHour
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

private val logger = getLogger("DrinkService")

class DrinkService(private val db: BeerDatabase) {

    suspend fun getDrinksForDay(date: LocalDate): List<DrinkRecord> {
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
        return drinks
    }

    suspend fun getDrinksForToday(): List<DrinkRecord> {
        val zone = TimeZone.currentSystemDefault()
        return getDrinksForDay(Clock.System.now().toLocalDateTime(zone).date)
    }

    suspend fun deleteDrinkById(id: Long): Unit {
        withContext(Dispatchers.IO) {
            db.drinkRecordQueries.deleteById(id)
        }
        logger.info("Deleted drink $id")
    }

    suspend fun insertDrink() {
        withContext(Dispatchers.IO) {
            addNewDrink(db)
        }
    }
}
