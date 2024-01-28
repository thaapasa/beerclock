package fi.tuska.beerclock.drinks

import fi.tuska.beerclock.images.DrinkImage
import fi.tuska.beerclock.logging.getLogger


private val logger = getLogger("DrinkCategory")

enum class Category(
    val image: DrinkImage,
    val nameEn: String,
    val nameFi: String,
    val order: Int,
) {
    BEERS(DrinkImage.CAT_BEERS, "Beers and Ciders", "Oluet ja siiderit", 1),
    WINES(DrinkImage.CAT_WINES, "Wines", "Viinit", 2),
    COCKTAILS(
        DrinkImage.CAT_COCKTAILS,
        "Cocktails and Mixed Drinks",
        "Cocktailit ja juomasekoitukset", 3
    ),
    SPIRITS(
        DrinkImage.CAT_SPIRITS,
        "Spirits and Liqueurs",
        "Väkevät alkoholijuomat ja liköörit", 4
    ),
    SPECIALITY(DrinkImage.CAT_SPECIALITY, "Speciality Drinks", "Erikoisjuomat", 7),
    LOW_ALCOHOL(DrinkImage.CAT_LOW_ALCOHOL, "Low-Alcohol Drinks", "Miedot alkoholijuomat", 8);

    companion object {
        fun forName(name: String): Category? = try {
            Category.valueOf(name)
        } catch (e: IllegalArgumentException) {
            logger.error("No drink category found with name $name. Using null category.")
            null
        }
    }
}
