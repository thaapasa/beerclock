package fi.tuska.beerclock.drinks

import fi.tuska.beerclock.database.DrinkRecord
import fi.tuska.beerclock.database.fromDbTime
import fi.tuska.beerclock.images.DrinkImage
import fi.tuska.beerclock.util.CommonParcelize
import fi.tuska.beerclock.util.CommonTypeParceler
import fi.tuska.beerclock.util.InstantParceler
import io.github.koalaplot.core.util.toString
import kotlinx.datetime.Instant

/**
 * Used to record that a drink has been drunk at a specified time.
 */
@CommonParcelize
class DrinkRecordInfo(
    val id: Long,
    @CommonTypeParceler<Instant, InstantParceler>
    /** When was this drink consumed */
    val time: Instant,
    private val info: BasicDrinkInfo,
) : BasicDrinkInfo(
    key = info.key,
    producer = info.producer,
    name = info.name,
    quantityCl = info.quantityCl,
    abvPercentage = info.abvPercentage,
    image = info.image,
    category = info.category,
    rating = info.rating,
    note = info.note,
) {

    override fun toString() =
        "$id: $name (${quantityCl.toString(1)} cl ${abvPercentage.toString(1)} %) at $time"

    companion object {
        fun fromRecord(record: DrinkRecord): DrinkRecordInfo = DrinkRecordInfo(
            id = record.id,
            time = Instant.fromDbTime(record.time),
            info = BasicDrinkInfo(
                key = "${record.id}-${record.version}",
                producer = record.producer,
                name = record.name,
                quantityCl = record.quantity_liters * 100,
                abvPercentage = record.abv * 100,
                image = DrinkImage.forName(record.image),
                category = record.category?.let { Category.forName(it) },
                rating = record.rating,
                note = record.note,
            )
        )
    }
}
