package fi.tuska.beerclock.drinks.mix

import fi.tuska.beerclock.database.MixedDrink
import fi.tuska.beerclock.database.toDbTime
import fi.tuska.beerclock.drinks.BasicDrinkInfo
import fi.tuska.beerclock.drinks.Category
import fi.tuska.beerclock.images.DrinkImage
import kotlinx.datetime.Clock

data class MixedDrinkInfo(
    val id: Long? = null,
    val name: String,
    val image: DrinkImage = DrinkImage.CAT_PUNCHES,
    val category: Category? = Category.COCKTAILS,
    val key: String = "$id-${Clock.System.now().toDbTime()}",
) {

    fun asDrinkInfo(): BasicDrinkInfo {
        return BasicDrinkInfo(
            key = key,
            name = name,
            image = image,
            category = category,
            abvPercentage = 0.0,
            quantityCl = 0.0,
        )
    }

    companion object {
        fun fromRecord(record: MixedDrink): MixedDrinkInfo = MixedDrinkInfo(
            id = record.id,
            name = record.name,
            image = DrinkImage.forName(record.image),
            category = record.category?.let(Category::forName),
            key = "${record.id}-${record.version}",
        )
    }
}
