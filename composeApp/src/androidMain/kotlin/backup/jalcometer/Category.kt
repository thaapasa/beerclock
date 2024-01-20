package fi.tuska.beerclock.backup.jalcometer

import android.database.Cursor
import fi.tuska.beerclock.database.toSequence
import fi.tuska.beerclock.drinks.Category

fun readCategories(ctx: ImportContext): Map<Long, JAlcoMeterCategory> {
    val cursor =
        ctx.db.query("SELECT id, name, icon FROM categories")
    val seq = cursor.toSequence()
    val categories = seq.map { JAlcoMeterCategory.fromDb(it) }.toList()
    return mapOf(*categories.map { Pair(it.id, it) }.toTypedArray())
}

data class JAlcoMeterCategory(
    val id: Long,
    val name: String,
    val icon: String,
) {
    val category = jAlcometerCategoryIconMapping[icon]

    companion object {
        fun fromDb(cursor: Cursor): JAlcoMeterCategory = with(cursor) {
            return JAlcoMeterCategory(
                id = getLong(0),
                name = getString(1),
                icon = getString(2),
            )
        }
    }

    override fun toString() = "$id: $name ($icon): $category"
}

val jAlcometerCategoryIconMapping = mapOf(
    "cat_beers" to Category.BEERS,
    "cat_cocktails" to Category.COCKTAILS,
    "cat_longdrinks" to Category.COCKTAILS,
    "cat_punches" to Category.COCKTAILS,
    "cat_spirits" to Category.SPIRITS,
    "cat_wines" to Category.WINES
)
