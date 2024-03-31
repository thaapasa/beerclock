package fi.tuska.beerclock.backup.jalcometer

import android.database.Cursor
import fi.tuska.beerclock.database.batchOperate
import fi.tuska.beerclock.database.toSequence
import fi.tuska.beerclock.drinks.DrinkDetailsFromEditor
import fi.tuska.beerclock.images.DrinkImage
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.logging.getLogger
import kotlinx.datetime.Clock

private val logger = getLogger("HistoryImporter")

fun importDrinkLibrary(
    ctx: ImportContext,
    categories: Map<Long, JAlcoMeterCategory>,
) {
    val rows = ctx.db.queryNumEntries("drinks")
    val strings = Strings.get()
    logger.info("There are $rows drinks in drinks library")
    ctx.showStatus(ImportStatus(strings.settings.importJAlcoMeterMsgImportingLibrary))
    val cursor =
        ctx.db.query(
            """
            SELECT d.id, d.name, d.cat_id, d.strength, d.icon, d.comment,
              s.name AS size_name, s.volume
            FROM drinks d
            JOIN (
                SELECT ds.drink_id, ds.size_id, ds.pos
                FROM drinks_sizes ds
                INNER JOIN (
                    SELECT drink_id, MIN(pos) AS min_pos
                    FROM drinks_sizes
                    GROUP BY drink_id
                ) AS min_ds ON ds.drink_id = min_ds.drink_id AND ds.pos = min_ds.min_pos
            ) AS first_size_mapping ON d.id = first_size_mapping.drink_id
            JOIN sizes s ON first_size_mapping.size_id = s.id
            ORDER BY first_size_mapping.pos;
            """.trimMargin()
        )
    val drinks = cursor.toSequence().map { JAlcoMeterDrink.fromDb(it, categories) }
    ctx.drinkService.db.batchOperate(drinks, batchSize = 250) {
        ctx.drinkService.operations.insertDrinkInfo(it.toEditorData())
    }
}


data class JAlcoMeterDrink(
    val id: Long,
    val name: String,
    val categoryId: Long,
    val strength: Double,
    val icon: String,
    val comment: String,
    val sizeName: String,
    val volume: Double,
    val jAlcoMeterCategory: JAlcoMeterCategory?,
) {
    val category = jAlcoMeterCategory?.let { jAlcometerCategoryIconMapping[it.icon] }
    val image = jAlcometerIconMapping[icon] ?: DrinkImage.GENERIC_DRINK

    companion object {
        fun fromDb(cursor: Cursor, categories: Map<Long, JAlcoMeterCategory>): JAlcoMeterDrink =
            with(cursor) {
                val categoryId = getLong(2)
                return JAlcoMeterDrink(
                    id = getLong(0),
                    name = getString(1),
                    categoryId = categoryId,
                    strength = getDouble(3),
                    icon = getString(4),
                    comment = getString(5),
                    sizeName = getString(6),
                    volume = getDouble(7),
                    jAlcoMeterCategory = categories[categoryId],
                )
            }
    }

    fun toEditorData(): DrinkDetailsFromEditor = DrinkDetailsFromEditor(
        producer = null,
        name = name,
        category = category,
        abv = strength / 100.0,
        quantityLiters = volume,
        time = Clock.System.now(),
        image = image,
        note = comment,
    )

    override fun toString() = "$id: $name, $sizeName ($volume l $strength %) $icon -> $category"
}
