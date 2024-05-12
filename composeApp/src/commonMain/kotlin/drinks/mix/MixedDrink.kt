package fi.tuska.beerclock.drinks.mix

import fi.tuska.beerclock.bac.BacFormulas
import fi.tuska.beerclock.database.GetMixedDrinkOverviews
import fi.tuska.beerclock.database.MixedDrink
import fi.tuska.beerclock.database.MixedDrinkComponent
import fi.tuska.beerclock.database.toDbTime
import fi.tuska.beerclock.drinks.BasicDrinkInfo
import fi.tuska.beerclock.drinks.Category
import fi.tuska.beerclock.images.DrinkImage
import fi.tuska.beerclock.settings.GlobalUserPreferences
import fi.tuska.beerclock.util.CommonParcelable
import fi.tuska.beerclock.util.CommonParcelize
import kotlinx.datetime.Clock
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

@CommonParcelize
data class MixedDrinkInfo(
    val id: Long? = null,
    val name: String,
    val instructions: String? = null,
    val image: DrinkImage = DrinkImage.CAT_PUNCHES,
    val category: Category? = Category.COCKTAILS,
    val key: String = "$id-${Clock.System.now().toDbTime()}",
) : CommonParcelable {

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
            instructions = record.instructions,
            image = DrinkImage.forName(record.image),
            category = record.category?.let(Category::forName),
            key = "${record.id}-${record.version}",
        )
    }
}


@CommonParcelize
data class MixedDrinkOverview(
    val info: MixedDrinkInfo,
    val quantityLiters: Double,
    val alcoholLiters: Double,
) : CommonParcelable {
    val key = info.key

    fun asDrinkInfo(): BasicDrinkInfo {
        return BasicDrinkInfo(
            key = key,
            name = info.name,
            image = info.image,
            category = info.category,
            abvPercentage = if (quantityLiters > 0.0) 100.0 * alcoholLiters / quantityLiters else 0.0,
            quantityCl = quantityLiters * 100.0,
        )
    }

    companion object {
        fun fromRecord(record: GetMixedDrinkOverviews): MixedDrinkOverview =
            MixedDrinkOverview(
                MixedDrinkInfo(
                    id = record.id,
                    name = record.name ?: "",
                    instructions = record.instructions,
                    image = record.image?.let(DrinkImage::forName) ?: DrinkImage.GENERIC_DRINK,
                    category = record.category?.let(Category::forName),
                    key = "${record.id}-${record.version}",
                ),
                quantityLiters = record.quantity_liters ?: 0.0,
                alcoholLiters = record.alcohol_liters ?: 0.0,
            )
    }
}


@CommonParcelize
data class MixedDrinkItem(
    val id: Long? = null,
    val name: String,
    val abvPercentage: Double,
    val quantityCl: Double,
    val key: String = "$id-${Clock.System.now().toDbTime()}",
) : CommonParcelable, KoinComponent {

    val prefs: GlobalUserPreferences = get()

    val totalAlcoholGrams =
        BacFormulas.getAlcoholGrams(quantityCl = quantityCl, abvPercentage = abvPercentage)

    fun units(): Double = BacFormulas.getUnitsFromAlcoholWeight(totalAlcoholGrams, prefs.prefs)

    companion object {
        fun fromRecord(record: MixedDrinkComponent): MixedDrinkItem = MixedDrinkItem(
            id = record.id,
            name = record.name,
            abvPercentage = record.abv,
            quantityCl = record.quantity_liters * 100.0,
            key = "${record.id}-${record.version}",
        )
    }
}


@CommonParcelize
data class MixedDrink(
    val info: MixedDrinkInfo,
    val items: List<MixedDrinkItem>,
) : CommonParcelable, KoinComponent {
    val id = info.id
    val key = info.key

    val prefs: GlobalUserPreferences = get()

    val totalQuantityCl = items.sumOf { it.quantityCl }
    val totalAlcoholGrams = items.sumOf { it.totalAlcoholGrams }
    val totalAlcoholCl = items.sumOf { it.quantityCl * it.abvPercentage / 100.0 }
    val totalAbv = if (totalQuantityCl > 0) (totalAlcoholCl / totalQuantityCl) * 100.0 else 0.0
    val totalUnits = BacFormulas.getUnitsFromAlcoholWeight(totalAlcoholGrams, prefs.prefs)
}
