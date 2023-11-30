package fi.tuska.beerclock.drinks

import fi.tuska.beerclock.database.DrinkRecord
import fi.tuska.beerclock.database.fromDbTime
import fi.tuska.beerclock.images.DrinkImage
import kotlinx.datetime.Instant

class DrinkRecordInfo(private val record: DrinkRecord) {
    val id = record.id

    /** Name of the drink */
    val name = record.name

    val image: DrinkImage = DrinkImage.forName(record.image)

    /** When was this drink consumed */
    val time = Instant.fromDbTime(record.time)

    /** Size of the drink, in cl */
    val quantityCl = record.quantity_liters / 100.0

    /** Strength of the drink, or alcohol by volume, as a percentage value (range: 0 - 100) */
    val abvPercentage = record.abv * 100

    /** Amount of alcohol in the drink, in liters */
    fun alcoholLiters(): Double = this.record.quantity_liters * this.record.abv

    /** Amount of alcohol in the drink, in grams */
    fun alcoholGrams(): Double = this.alcoholLiters() * Constants.alcoholDensity

}
