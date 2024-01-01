package fi.tuska.beerclock.drinks

import fi.tuska.beerclock.images.CategoryImage
import fi.tuska.beerclock.logging.getLogger


private val logger = getLogger("DrinkCategory")

enum class Category(
    val image: CategoryImage,
    val nameEn: String,
    val nameFi: String,
    val order: Int,
) {
    BEERS(CategoryImage.CAT_BEERS, "Beers", "Oluet", 1),
    CIDERS(CategoryImage.CAT_CIDERS, "Ciders and Meads", "Siiderit ja simat", 2),
    WINES(CategoryImage.CAT_WINES, "Wines", "Viinit", 3),
    COCKTAILS(
        CategoryImage.CAT_COCKTAILS,
        "Cocktails and Mixed Drinks",
        "Cocktailit ja juomasekoitukset", 4
    ),
    SPIRITS(
        CategoryImage.CAT_SPIRITS,
        "Spirits and Liqueurs",
        "Väkevät alkoholijuomat ja liköörit", 5
    ),
    WARM_DRINKS(
        CategoryImage.CAT_WARM_DRINKS,
        "Warm Alcoholic Drinks",
        "Lämpimät alkoholijuomat",
        6
    ),
    SPECIALITY(CategoryImage.CAT_SPECIALITY, "Speciality Drinks", "Erikoisjuomat", 7),
    LOW_ALCOHOL(CategoryImage.CAT_LOW_ALCOHOL, "Low-Alcohol Drinks", "Miedot alkoholijuomat", 8);

    companion object {
        fun forName(name: String): Category? = try {
            Category.valueOf(name)
        } catch (e: IllegalArgumentException) {
            logger.error("No drink category found with name $name. Using null category.")
            null
        }
    }
}
