package fi.tuska.beerclock.drinks

import fi.tuska.beerclock.database.DrinkRecord
import fi.tuska.beerclock.database.fromDbTime
import fi.tuska.beerclock.images.DrinkImage
import io.github.koalaplot.core.util.toString
import kotlinx.datetime.Instant

/**
 * Used to record that a drink has been drunk at a specified time.
 */
class DrinkRecordInfo(record: DrinkRecord) : BasicDrinkInfo(
    producer = record.producer,
    name = record.name,
    quantityCl = record.quantity_liters * 100,
    abvPercentage = record.abv * 100,
    image = DrinkImage.forName(record.image),
    category = record.category?.let { Category.forName(it) },
    note = record.note,
) {
    val id = record.id
    override val key = record.id

    /** When was this drink consumed */
    val time = Instant.fromDbTime(record.time)

    override fun toString() =
        "$id: $name (${quantityCl.toString(1)} cl ${abvPercentage.toString(1)} %) at $time"
}
