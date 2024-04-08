package fi.tuska.beerclock.backup.jalcometer

import android.database.Cursor
import fi.tuska.beerclock.database.batchOperate
import fi.tuska.beerclock.database.toSequence
import fi.tuska.beerclock.drinks.DrinkDetailsFromEditor
import fi.tuska.beerclock.drinks.DrinkInfo
import fi.tuska.beerclock.images.DrinkImage
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.logging.getLogger
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toKotlinLocalDateTime
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private val logger = getLogger("HistoryImporter")

fun importHistory(ctx: ImportContext) {
    val rows = ctx.db.queryNumEntries("history")
    val strings = Strings.get()
    logger.info("There are $rows rows of history")
    val drinks = ctx.drinkService.operations.getDrinkLibrary()
    val drinkMap = mapOf(*drinks.map { Pair(it.name, it) }.toTypedArray())

    val cursor =
        ctx.db.query("SELECT id, name, strength, volume, icon, time, comment FROM history")
    val seq = cursor.toSequence()
    ctx.drinkService.db.batchOperate(
        seq.map { JAlcometerHistory.fromDb(it) },
        250,
        afterEach = {
            ctx.showStatus(
                ImportStatus(
                    strings.settings.importJAlcoMeterMsgImportingDrink(it, rows),
                    it.toFloat() / rows.toFloat()
                )
            )
        }
    ) { row -> importHistoryRow(ctx, row, drinkMap) }
}

fun importHistoryRow(ctx: ImportContext, row: JAlcometerHistory, drinkMap: Map<String, DrinkInfo>) {
    ctx.drinkService.operations.importDrink(row.id, row.toEditorData(drinkMap))
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
        fun fromDb(cursor: Cursor): JAlcometerHistory =
            with(cursor) {
                return JAlcometerHistory(
                    id = getLong(0),
                    name = getString(1),
                    strength = getDouble(2),
                    volume = getDouble(3),
                    icon = getString(4),
                    dbTime = getString(5),
                    comment = getString(6),
                )
            }
    }

    fun toEditorData(drinkMap: Map<String, DrinkInfo>): DrinkDetailsFromEditor =
        DrinkDetailsFromEditor(
            name = name,
            producer = "",
            category = drinkMap[name]?.category,
            abv = strength / 100.0,
            quantityLiters = volume,
            time = time,
            image = image,
            rating = null,
            note = comment.ifBlank { null },
        )

    override fun toString() = "$id: $name ($volume l $strength %) $image @ $time"
}

private val JAlcoMeterTimeZone = TimeZone.of("Europe/Helsinki")
private val TimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

fun Instant.Companion.fromJAlcometerTime(time: String): Instant {
    return LocalDateTime.parse(time, TimeFormat).toKotlinLocalDateTime().toInstant(
        JAlcoMeterTimeZone
    )
}
