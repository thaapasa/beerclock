package fi.tuska.beerclock.drinks

import fi.tuska.beerclock.images.DrinkImage

enum class Category(val image: DrinkImage, nameEn: String, nameFi: String) {
    BEERS(DrinkImage.BEER_GLASS_1, "Beers", "Oluet"),
    CIDERS(DrinkImage.GENERIC_DRINK, "Ciders and Meads", "Siiderit ja simat"),
    WINES(DrinkImage.WHITE_WINE_BOTTLE_2, "Wines", "Viinit"),
    COCKTAILS(
        DrinkImage.GENERIC_DRINK,
        "Cocktails and Mixed Drinks",
        "Cocktailit ja juomasekoitukset"
    ),
    SPIRITS(DrinkImage.GENERIC_DRINK, "Spirits and Liqueurs", "Väkevät alkoholijuomat ja liköörit"),
    WARM_DRINKS(DrinkImage.GENERIC_DRINK, "Warm Alcoholic Drinks", "Lämpimät alkoholijuomat"),
    SPECIALITY(DrinkImage.GENERIC_DRINK, "Speciality Drinks", "Erikoisjuomat"),
    LOW_ALCOHOL(DrinkImage.GENERIC_DRINK, "Low-Alcohol Drinks", "Miedot alkoholijuomat");
}
