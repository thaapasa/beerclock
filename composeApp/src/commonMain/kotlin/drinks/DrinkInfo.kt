package fi.tuska.beerclock.drinks

import fi.tuska.beerclock.database.DrinkLibrary
import fi.tuska.beerclock.images.DrinkImage

/**
 * An item in the drink library. These can be searched from new drink screen and used
 * to create new drink record entries.
 */
class DrinkInfo(record: DrinkLibrary) : BasicDrinkInfo(
    producer = record.producer,
    name = record.name,
    quantityCl = record.quantity_liters * 100,
    abvPercentage = record.abv * 100,
    image = DrinkImage.forName(record.image),
    category = record.category?.let { Category.forName(it) },
    rating = record.rating,
    note = record.note,
) {
    val id = record.id
    override val key = record.id

    override fun toString() = "$name ($quantityCl cl $abvPercentage %, ${category ?: "-"})"
}
