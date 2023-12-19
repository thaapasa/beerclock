package fi.tuska.beerclock.drinks

import fi.tuska.beerclock.database.SelectLatestDrinks
import fi.tuska.beerclock.images.DrinkImage

/**
 * Used to convey information about latest drinks that have been consumed,
 * without taking into account the specific times.
 */
class LatestDrinkInfo(record: SelectLatestDrinks) : BasicDrinkInfo(
    name = record.name,
    quantityCl = record.quantity_liters * 100,
    abvPercentage = record.abv * 100,
    image = DrinkImage.forName(record.image),
    category = record.category?.let { Category.forName(it) }
) {
    override val key = record.name
    override fun toString() = "$name ($quantityCl cl $abvPercentage %)"
}
