package fi.tuska.beerclock.drinks

import fi.tuska.beerclock.database.DrinkLibrary
import fi.tuska.beerclock.images.DrinkImage
import fi.tuska.beerclock.util.CommonParcelize

/**
 * An item in the drink library. These can be searched from new drink screen and used
 * to create new drink record entries.
 */
@CommonParcelize
class DrinkInfo(
    val id: Long,
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

    override fun toString() = "$name ($quantityCl cl $abvPercentage %, ${category ?: "-"})"

    companion object {
        fun fromRecord(record: DrinkLibrary): DrinkInfo = DrinkInfo(
            id = record.id,
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
