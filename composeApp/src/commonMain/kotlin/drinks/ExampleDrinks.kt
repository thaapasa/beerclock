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
    DrinkDef(
        Category.BEERS,
        l("Bottle of beer", "Pullo olutta"),
        33.0,
        4.6,
        DrinkImage.BEER_BOTTLE_2
    ),
    DrinkDef(Category.BEERS, l("Glass of beer", "Lasi olutta"), 33.0, 4.6, DrinkImage.BEER_GLASS_2),
    DrinkDef(Category.BEERS, l("Large beer", "Tuoppi"), 50.0, 4.6, DrinkImage.BEER_GLASS_1),
    DrinkDef(
        Category.BEERS,
        l("Beer can (33 cl)", "Olut (33 cl)"),
        33.0,
        4.6,
        DrinkImage.BEER_CAN_1
    ),
    DrinkDef(
        Category.BEERS,
        l("Beer can (50 cl)", "Olut (50 cl)"),
        50.0,
        4.6,
        DrinkImage.BEER_CAN_6
    ),
    DrinkDef(
        Category.CIDERS,
        l("Cider", "Siideri"),
        33.0,
        4.7,
        DrinkImage.CIDER_BOTTLE_1
    ),
    DrinkDef(
        Category.CIDERS,
        l("Honey Mead", "Hunajaviini (sima)"),
        20.0,
        10.0,
        DrinkImage.MEAD_1
    ),
    DrinkDef(
        Category.CIDERS,
        l("Gin Long Drink", "Lonkero"),
        33.0,
        4.7,
        DrinkImage.LONKERO_1
    ),
    DrinkDef(Category.WINES, l("Red Wine", "Punaviini"), 12.0, 13.0, DrinkImage.RED_WINE_GLASS_1),
    DrinkDef(
        Category.WINES,
        l("White Wine", "Valkoviini"),
        12.0,
        12.0,
        DrinkImage.WHITE_WINE_GLASS_1
    ),
    DrinkDef(
        Category.WINES,
        l("Champagne", "Samppanja"),
        12.0,
        12.0,
        DrinkImage.CHAMPAGNE_GLASS_1
    ),
    DrinkDef(
        Category.WINES,
        l("Dessert wine", "Jälkiruokaviini"),
        4.0,
        18.0,
        DrinkImage.DESSERT_WINE_1
    ),
    DrinkDef(Category.COCKTAILS, "Margarita", 10.0, 20.0, DrinkImage.MARGARITA_1),
    DrinkDef(Category.COCKTAILS, "Martini", 10.0, 18.0, DrinkImage.MARTINI_1),
    DrinkDef(
        Category.COCKTAILS,
        l("Gin and tonic", "Gin tonic"),
        11.0,
        14.0,
        DrinkImage.GIN_TONIC_1
    ),
    DrinkDef(
        Category.COCKTAILS,
        "Margarita",
        6.0,
        26.0,
        DrinkImage.MARGARITA_1
    ),
    DrinkDef(
        Category.COCKTAILS,
        l("White russian", "Valkovenäläinen"),
        11.0,
        16.0,
        DrinkImage.WHITE_RUSSIAN_1
    ),
    DrinkDef(
        Category.COCKTAILS,
        "Mojito",
        10.0,
        16.0,
        DrinkImage.MOJITO_1
    ),
    DrinkDef(Category.SPIRITS, l("Cognac", "Konjakki"), 4.0, 43.0, DrinkImage.COGNAC_1),
    DrinkDef(Category.SPIRITS, l("Whisky", "Viski"), 4.0, 43.0, DrinkImage.WHISKY_1),
    DrinkDef(Category.SPIRITS, l("Rum", "Rommi"), 4.0, 40.0, DrinkImage.RUM_1),
    DrinkDef(Category.SPIRITS, "Vana Tallinn", 4.0, 40.0, DrinkImage.LIQUEUR_1),
    DrinkDef(Category.SPIRITS, "Unicum", 4.0, 40.0, DrinkImage.LIQUEUR_2),
    DrinkDef(Category.SPIRITS, "Fernet Branca", 4.0, 39.0, DrinkImage.LIQUEUR_3),
    DrinkDef(Category.SPIRITS, "Vodka", 4.0, 40.0, DrinkImage.VODKA_1),
    DrinkDef(Category.SPIRITS, "Gin", 4.0, 40.0, DrinkImage.GIN_1),
    DrinkDef(Category.SPIRITS, "Tequila", 4.0, 38.0, DrinkImage.TEQUILA_1),
    DrinkDef(Category.WARM_DRINKS, "Irish Coffee", 24.0, 7.0, DrinkImage.IRISH_COFFEE_1),
    DrinkDef(Category.WARM_DRINKS, l("Mulled Wine", "Glögi"), 20.0, 10.0, DrinkImage.MULLED_WINE_1),
    DrinkDef(Category.SPECIALITY, "Sake", 10.0, 17.0, DrinkImage.SAKE_GLASS_1),
    DrinkDef(Category.SPECIALITY, l("Absinthe", "Absintti"), 4.0, 68.0, DrinkImage.ABSINTHE_1),
    DrinkDef(Category.LOW_ALCOHOL, "Shandy", 50.0, 2.5, DrinkImage.SHANDY_1),
    DrinkDef(Category.LOW_ALCOHOL, "Spritzer", 20.0, 6.5, DrinkImage.SPRITZER_1),
    DrinkDef(Category.LOW_ALCOHOL, "Mocktail", 25.0, 0.0, DrinkImage.MOCKTAIL_1),
    DrinkDef(
        Category.LOW_ALCOHOL,
        l("Glass of water", "Lasi vettä"),
        20.0,
        0.0,
        DrinkImage.WATER_GLASS_1
    ),
)


val ExampleDrinksFi = createExampleDrinks { _, fi -> fi }
val ExampleDrinksEn = createExampleDrinks { en, _ -> en }

fun exampleDrinks() = when (Strings.userLanguage()) {
    "fi" -> ExampleDrinksFi
    else -> ExampleDrinksEn
}
