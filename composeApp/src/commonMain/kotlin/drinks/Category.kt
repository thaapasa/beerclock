package fi.tuska.beerclock.drinks

import fi.tuska.beerclock.images.CategoryImage
import fi.tuska.beerclock.images.DrinkImage
import fi.tuska.beerclock.logging.getLogger

private val logger = getLogger("DrinkCategory")

enum class Category(val image: CategoryImage, nameEn: String, nameFi: String) {
    BEERS(CategoryImage.CAT_BEERS, "Beers", "Oluet"),
    CIDERS(CategoryImage.CAT_CIDERS, "Ciders and Meads", "Siiderit ja simat"),
    WINES(CategoryImage.CAT_WINES, "Wines", "Viinit"),
    COCKTAILS(
        CategoryImage.CAT_COCKTAILS,
        "Cocktails and Mixed Drinks",
        "Cocktailit ja juomasekoitukset"
    ),
    SPIRITS(CategoryImage.CAT_SPIRITS, "Spirits and Liqueurs", "Väkevät alkoholijuomat ja liköörit"),
    WARM_DRINKS(CategoryImage.CAT_WARM_DRINKS, "Warm Alcoholic Drinks", "Lämpimät alkoholijuomat"),
    SPECIALITY(CategoryImage.CAT_SPECIALITY, "Speciality Drinks", "Erikoisjuomat"),
    LOW_ALCOHOL(CategoryImage.CAT_LOW_ALCOHOL, "Low-Alcohol Drinks", "Miedot alkoholijuomat");

    companion object {
        fun forName(name: String): Category? = try {
            Category.valueOf(name)
        } catch (e: IllegalArgumentException) {
            logger.error("No drink category found with name $name. Using null category.")
            null
        }
    }
}
