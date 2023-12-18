package fi.tuska.beerclock.drinks

import fi.tuska.beerclock.images.DrinkImage
import fi.tuska.beerclock.localization.Strings

class DrinkDef(
    category: Category? = null,
    name: String,
    quantityCl: Double = 10.0,
    abvPercentage: Double = 5.0,
    image: DrinkImage = DrinkImage.GENERIC_DRINK,
) :
    BasicDrinkInfo(
        category = category,
        name = name,
        quantityCl = quantityCl,
        abvPercentage = abvPercentage,
        image = image,
    ) {
    override val key = name
}

fun createExampleDrinks(l: (en: String, fi: String) -> String) = listOf(
    DrinkDef(Category.BEERS, l("Large Lager", "Iso lager"), 50.0, 4.6, DrinkImage.BEER_GLASS_1),
    DrinkDef(Category.BEERS, l("Small Stout", "Pieni Stout"), 33.0, 4.6, DrinkImage.BEER_CAN_1),
    DrinkDef(Category.CIDERS, l("Apple Cider", "Omenasiideri"), 33.0, 4.6, DrinkImage.BEER_CAN_1),
    DrinkDef(
        Category.CIDERS,
        l("Honey Mead", "Hunajaviini (sima)"),
        20.0,
        10.0,
        DrinkImage.BEER_CAN_1
    ),
    DrinkDef(Category.WINES, l("Red Wine", "Punaviini"), 16.0, 13.0, DrinkImage.RED_WINE_GLASS_1),
    DrinkDef(
        Category.WINES,
        l("White Wine", "Valkoviini"),
        12.0,
        12.0,
        DrinkImage.WHITE_WINE_GLASS_1
    ),
    DrinkDef(
        Category.WINES,
        l("Champagne", "Shamppanja"),
        12.0,
        12.0,
        DrinkImage.WHITE_WINE_GLASS_1
    ),
    DrinkDef(Category.COCKTAILS, "Margarita", 10.0, 20.0, DrinkImage.MARTINI_1),
    DrinkDef(Category.COCKTAILS, "Martini", 10.0, 18.0, DrinkImage.MARTINI_1),
    DrinkDef(Category.SPIRITS, l("Cognac", "Konjakki"), 4.0, 43.0, DrinkImage.COGNAC_1),
    DrinkDef(Category.SPIRITS, l("Whisky", "Viski"), 4.0, 43.0, DrinkImage.COGNAC_1),
    DrinkDef(Category.SPIRITS, "Vodka", 4.0, 40.0, DrinkImage.COGNAC_1),
    DrinkDef(Category.WARM_DRINKS, "Irish Coffee", 20.0, 12.0, DrinkImage.COGNAC_1),
    DrinkDef(Category.WARM_DRINKS, l("Mulled Wine", "Glögi"), 20.0, 10.0, DrinkImage.COGNAC_1),
    DrinkDef(Category.SPECIALITY, "Sake", 10.0, 17.0, DrinkImage.COGNAC_1),
    DrinkDef(Category.LOW_ALCOHOL, "Shandy", 50.0, 2.5, DrinkImage.COGNAC_1),
    DrinkDef(Category.LOW_ALCOHOL, "Spritzer", 20.0, 6.5, DrinkImage.COGNAC_1),
)


val ExampleDrinksFi = createExampleDrinks { _, fi -> fi }
val ExampleDrinksEn = createExampleDrinks { en, _ -> en }

fun exampleDrinks() = when (Strings.userLanguage()) {
    "fi" -> ExampleDrinksFi
    else -> ExampleDrinksEn
}
