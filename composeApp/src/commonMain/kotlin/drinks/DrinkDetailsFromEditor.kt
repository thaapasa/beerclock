package fi.tuska.beerclock.drinks

import fi.tuska.beerclock.images.DrinkImage
import fi.tuska.beerclock.images.toDrinkImage
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

data class DrinkDetailsFromEditor(
    val name: String,
    val producer: String?,
    val category: Category?,
    val abv: Double,
    val quantityLiters: Double,
    val time: Instant,
    val image: DrinkImage,
    val note: String?,
) {
    companion object {

        fun fromBasicInfo(basic: BasicDrinkInfo, time: Instant): DrinkDetailsFromEditor {
            return DrinkDetailsFromEditor(
                name = basic.name,
                producer = basic.producer,
                category = basic.category,
                abv = basic.abvPercentage / 100.0,
                quantityLiters = basic.quantityCl / 100.0,
                image = basic.image.toDrinkImage(),
                time = time,
                note = basic.note,
            )
        }

        fun fromBasicInfo(basic: BasicDrinkInfo, date: LocalDate?): DrinkDetailsFromEditor {
            val time = DrinkTimeService().defaultDrinkTime(date)
            return fromBasicInfo(basic, time)
        }

        fun fromRecord(drink: DrinkRecordInfo): DrinkDetailsFromEditor {
            return fromBasicInfo(drink, drink.time)
        }
    }
}
