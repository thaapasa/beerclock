package fi.tuska.beerclock.drinks.mix

import fi.tuska.beerclock.database.MixedDrink
import fi.tuska.beerclock.drinks.BasicDrinkInfo
import fi.tuska.beerclock.drinks.Category
import fi.tuska.beerclock.images.DrinkImage

data class MixedDrinkInfo(
    val name: String,
    val image: DrinkImage = DrinkImage.CAT_PUNCHES,
    val category: Category? = Category.COCKTAILS,
    val id: Long? = null,
) {

    fun asDrinkInfo(): BasicDrinkInfo {
        return BasicDrinkInfo(
            key = id?.toString() ?: "new",
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
        )
    }
}
