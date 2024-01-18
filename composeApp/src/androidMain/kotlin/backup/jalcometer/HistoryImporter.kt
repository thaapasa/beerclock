package fi.tuska.beerclock.backup.jalcometer

import android.database.Cursor
import fi.tuska.beerclock.database.BeerDatabase
import fi.tuska.beerclock.database.batchOperate
import fi.tuska.beerclock.database.toDbTime
import fi.tuska.beerclock.database.toSequence
import fi.tuska.beerclock.images.DrinkImage
import fi.tuska.beerclock.logging.getLogger
import io.requery.android.database.sqlite.SQLiteDatabase
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toKotlinLocalDateTime
import org.koin.java.KoinJavaComponent.inject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private val logger = getLogger("HistoryImporter")

fun importHistory(srcDb: SQLiteDatabase) {
    val targetDb: BeerDatabase by inject(BeerDatabase::class.java)
    val rows = srcDb.queryNumEntries("history")
    logger.info("There are $rows rows of history")
    val cursor =
        srcDb.query("SELECT id, name, strength, volume, icon, time, comment FROM history ORDER BY time DESC")
    val seq = cursor.toSequence()
    targetDb.batchOperate(
        seq.map(JAlcometerHistory::fromDb),
        500,
    ) { row -> importHistoryRow(targetDb, row) }
}

fun importHistoryRow(db: BeerDatabase, row: JAlcometerHistory) {
    db.drinkRecordQueries.import(
        importId = row.id,
        time = row.time.toDbTime(),
        name = row.name,
        image = row.image.name,
        abv = row.strength / 100.0,
        quantityLiters = row.volume,
        category = null
    )
}

data class JAlcometerHistory(
    val id: Long,
    val name: String,
    val strength: Double,
    val volume: Double,
    val icon: String,
    val dbTime: String,
    val comment: String,
) {
    val image = jAlcometerIconMapping[icon] ?: DrinkImage.GENERIC_DRINK
    val time = Instant.fromJAlcometerTime(dbTime)

    companion object {
        fun fromDb(cursor: Cursor): JAlcometerHistory = with(cursor) {
            return JAlcometerHistory(
                id = getLong(0),
                name = getString(1),
                strength = getDouble(2),
                volume = getDouble(3),
                icon = getString(4),
                dbTime = getString(5),
                comment = getString(6)
            )
        }
    }

    override fun toString() = "$id: $name ($volume l $strength %) $image @ $time"
}

private val JAlcoMeterTimeZone = TimeZone.of("Europe/Helsinki")
private val TimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

fun Instant.Companion.fromJAlcometerTime(time: String): Instant {
    return LocalDateTime.parse(time, TimeFormat).toKotlinLocalDateTime().toInstant(
        JAlcoMeterTimeZone
    )
}
