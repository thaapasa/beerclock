package fi.tuska.beerclock.drinks

import fi.tuska.beerclock.database.DrinkRecord
import fi.tuska.beerclock.database.fromDbTime
import fi.tuska.beerclock.images.DrinkImage
import kotlinx.datetime.Instant

/**
 * Used to record that a drink has been drunk at a specified time.
 */
class DrinkRecordInfo(record: DrinkRecord) : BasicDrinkInfo(
    name = record.name,
    quantityCl = record.quantity_liters * 100,
    abvPercentage = record.abv * 100,
    image = DrinkImage.forName(record.image),
) {
    val id = record.id
    override val key = record.id

    /** When was this drink consumed */
    val time = Instant.fromDbTime(record.time)
}
