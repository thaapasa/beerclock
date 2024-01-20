package fi.tuska.beerclock.backup.jalcometer

import android.database.Cursor
import fi.tuska.beerclock.database.BeerDatabase
import fi.tuska.beerclock.database.toSequence
import fi.tuska.beerclock.images.DrinkImage
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.logging.getLogger
import io.requery.android.database.sqlite.SQLiteDatabase
import org.koin.java.KoinJavaComponent


private val logger = getLogger("HistoryImporter")

fun importDrinkLibrary(
    srcDb: SQLiteDatabase,
    targetDb: BeerDatabase,
    categories: Map<Long, JAlcoMeterCategory>,
    showStatus: (status: ImportStatus) -> Unit,
) {
    val targetDb: BeerDatabase by KoinJavaComponent.inject(BeerDatabase::class.java)
    val rows = srcDb.queryNumEntries("drinks")
    val strings = Strings.get()
    logger.info("There are $rows drinks in drinks library")
    showStatus(ImportStatus(strings.settings.importMsgImportingLibrary))
    val cursor =
        srcDb.query(
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
    val drinks = cursor.toSequence().map { JAlcoMeterDrink.fromDb(it, categories) }.toList()
    logger.info("Drinks: ${drinks.joinToString("\n")}")
    targetDb.transaction {
        drinks.forEach {
            targetDb.drinkLibraryQueries.insert(
                name = it.name,
                category = it.category?.name,
                quantityLiters = it.volume,
                abv = it.strength / 100.0,
                image = it.image.name,
            )
        }
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
                    jAlcoMeterCategory = categories[categoryId]
                )
            }
    }

    override fun toString() = "$id: $name, $sizeName ($volume l $strength %) $icon -> $category"
}
