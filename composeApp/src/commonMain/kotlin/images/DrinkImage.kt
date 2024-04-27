package fi.tuska.beerclock.images

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import beerclock.composeapp.generated.resources.Res
import beerclock.composeapp.generated.resources.cat_all
import beerclock.composeapp.generated.resources.cat_beer
import beerclock.composeapp.generated.resources.cat_ciders
import beerclock.composeapp.generated.resources.cat_cocktails
import beerclock.composeapp.generated.resources.cat_low_alcohol
import beerclock.composeapp.generated.resources.cat_punches
import beerclock.composeapp.generated.resources.cat_speciality
import beerclock.composeapp.generated.resources.cat_spirits
import beerclock.composeapp.generated.resources.cat_uncategorized
import beerclock.composeapp.generated.resources.cat_warm_drinks
import beerclock.composeapp.generated.resources.cat_wines
import beerclock.composeapp.generated.resources.drink_absinthe_1
import beerclock.composeapp.generated.resources.drink_baileys_glass_1
import beerclock.composeapp.generated.resources.drink_beer_bottle_1
import beerclock.composeapp.generated.resources.drink_beer_bottle_2
import beerclock.composeapp.generated.resources.drink_beer_bottle_3
import beerclock.composeapp.generated.resources.drink_beer_can_1
import beerclock.composeapp.generated.resources.drink_beer_can_2
import beerclock.composeapp.generated.resources.drink_beer_can_3
import beerclock.composeapp.generated.resources.drink_beer_can_4
import beerclock.composeapp.generated.resources.drink_beer_can_5
import beerclock.composeapp.generated.resources.drink_beer_can_6
import beerclock.composeapp.generated.resources.drink_beer_glass_1
import beerclock.composeapp.generated.resources.drink_beer_glass_2
import beerclock.composeapp.generated.resources.drink_beer_glass_3
import beerclock.composeapp.generated.resources.drink_beer_glass_4
import beerclock.composeapp.generated.resources.drink_beer_glass_5
import beerclock.composeapp.generated.resources.drink_beer_stein_1
import beerclock.composeapp.generated.resources.drink_bloody_mary_1
import beerclock.composeapp.generated.resources.drink_caipirosca_1
import beerclock.composeapp.generated.resources.drink_caipirosca_2
import beerclock.composeapp.generated.resources.drink_champagne_bottle_1
import beerclock.composeapp.generated.resources.drink_champagne_bottle_2
import beerclock.composeapp.generated.resources.drink_champagne_glass_1
import beerclock.composeapp.generated.resources.drink_cider_bottle_1
import beerclock.composeapp.generated.resources.drink_cider_glass_1
import beerclock.composeapp.generated.resources.drink_cognac_1
import beerclock.composeapp.generated.resources.drink_cosmopolitan_1
import beerclock.composeapp.generated.resources.drink_cream_liqueur_bottle_1
import beerclock.composeapp.generated.resources.drink_daiquiri_1
import beerclock.composeapp.generated.resources.drink_dessert_wine_1
import beerclock.composeapp.generated.resources.drink_dessert_wine_2
import beerclock.composeapp.generated.resources.drink_generic_drink
import beerclock.composeapp.generated.resources.drink_gin_1
import beerclock.composeapp.generated.resources.drink_gin_bottle_1
import beerclock.composeapp.generated.resources.drink_gin_tonic_1
import beerclock.composeapp.generated.resources.drink_hot_shot_1
import beerclock.composeapp.generated.resources.drink_iced_tea_1
import beerclock.composeapp.generated.resources.drink_irish_coffee_1
import beerclock.composeapp.generated.resources.drink_liqueur_1
import beerclock.composeapp.generated.resources.drink_liqueur_2
import beerclock.composeapp.generated.resources.drink_liqueur_3
import beerclock.composeapp.generated.resources.drink_lonkero_1
import beerclock.composeapp.generated.resources.drink_margarita_1
import beerclock.composeapp.generated.resources.drink_martini_1
import beerclock.composeapp.generated.resources.drink_mead_1
import beerclock.composeapp.generated.resources.drink_mixed_drink_bottle_1
import beerclock.composeapp.generated.resources.drink_mocktail_1
import beerclock.composeapp.generated.resources.drink_mocktail_2
import beerclock.composeapp.generated.resources.drink_mojito_1
import beerclock.composeapp.generated.resources.drink_mulled_wine_1
import beerclock.composeapp.generated.resources.drink_pina_colada_1
import beerclock.composeapp.generated.resources.drink_poison_1
import beerclock.composeapp.generated.resources.drink_punch_1
import beerclock.composeapp.generated.resources.drink_punch_2
import beerclock.composeapp.generated.resources.drink_red_wine_bottle_1
import beerclock.composeapp.generated.resources.drink_red_wine_bottle_2
import beerclock.composeapp.generated.resources.drink_red_wine_glass_1
import beerclock.composeapp.generated.resources.drink_rum_1
import beerclock.composeapp.generated.resources.drink_rum_bottle_1
import beerclock.composeapp.generated.resources.drink_rum_cola_1
import beerclock.composeapp.generated.resources.drink_sake_bottle_1
import beerclock.composeapp.generated.resources.drink_sake_glass_1
import beerclock.composeapp.generated.resources.drink_shandy_1
import beerclock.composeapp.generated.resources.drink_spritzer_1
import beerclock.composeapp.generated.resources.drink_tequila_1
import beerclock.composeapp.generated.resources.drink_vodka_1
import beerclock.composeapp.generated.resources.drink_vodka_bottle_1
import beerclock.composeapp.generated.resources.drink_vodka_bottle_2
import beerclock.composeapp.generated.resources.drink_water_glass_1
import beerclock.composeapp.generated.resources.drink_whisky_1
import beerclock.composeapp.generated.resources.drink_whisky_2
import beerclock.composeapp.generated.resources.drink_whisky_3
import beerclock.composeapp.generated.resources.drink_whisky_bottle_1
import beerclock.composeapp.generated.resources.drink_white_russian_1
import beerclock.composeapp.generated.resources.drink_white_wine_bottle_1
import beerclock.composeapp.generated.resources.drink_white_wine_bottle_2
import beerclock.composeapp.generated.resources.drink_white_wine_bottle_3
import beerclock.composeapp.generated.resources.drink_white_wine_glass_1
import beerclock.composeapp.generated.resources.drink_white_wine_glass_2
import fi.tuska.beerclock.logging.getLogger
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

private val logger = getLogger("DrinkImage")

@OptIn(ExperimentalResourceApi::class)
enum class DrinkImage(val drawable: DrawableResource) : Image {
    CAT_BEERS(Res.drawable.cat_beer),
    CAT_CIDERS(Res.drawable.cat_ciders),
    BEER_GLASS_1(Res.drawable.drink_beer_glass_1),
    BEER_GLASS_2(Res.drawable.drink_beer_glass_2),
    BEER_STEIN_1(Res.drawable.drink_beer_stein_1),
    BEER_GLASS_5(Res.drawable.drink_beer_glass_5),
    CIDER_GLASS_1(Res.drawable.drink_cider_glass_1),
    SHANDY_1(Res.drawable.drink_shandy_1),
    MEAD_1(Res.drawable.drink_mead_1),
    BEER_GLASS_3(Res.drawable.drink_beer_glass_3),
    BEER_GLASS_4(Res.drawable.drink_beer_glass_4),
    BEER_BOTTLE_1(Res.drawable.drink_beer_bottle_1),
    BEER_BOTTLE_2(Res.drawable.drink_beer_bottle_2),
    BEER_BOTTLE_3(Res.drawable.drink_beer_bottle_3),
    CIDER_BOTTLE_1(Res.drawable.drink_cider_bottle_1),
    MIXED_DRINK_BOTTLE_1(Res.drawable.drink_mixed_drink_bottle_1),
    BEER_CAN_1(Res.drawable.drink_beer_can_1),
    BEER_CAN_2(Res.drawable.drink_beer_can_2),
    BEER_CAN_3(Res.drawable.drink_beer_can_3),
    BEER_CAN_4(Res.drawable.drink_beer_can_4),
    BEER_CAN_5(Res.drawable.drink_beer_can_5),
    BEER_CAN_6(Res.drawable.drink_beer_can_6),
    LONKERO_1(Res.drawable.drink_lonkero_1),
    CAT_WINES(Res.drawable.cat_wines),
    RED_WINE_GLASS_1(Res.drawable.drink_red_wine_glass_1),
    WHITE_WINE_GLASS_1(Res.drawable.drink_white_wine_glass_1),
    WHITE_WINE_GLASS_2(Res.drawable.drink_white_wine_glass_2),
    CHAMPAGNE_GLASS_1(Res.drawable.drink_champagne_glass_1),
    SPRITZER_1(Res.drawable.drink_spritzer_1),
    DESSERT_WINE_1(Res.drawable.drink_dessert_wine_1),
    DESSERT_WINE_2(Res.drawable.drink_dessert_wine_2),
    RED_WINE_BOTTLE_1(Res.drawable.drink_red_wine_bottle_1),
    RED_WINE_BOTTLE_2(Res.drawable.drink_red_wine_bottle_2),
    WHITE_WINE_BOTTLE_1(Res.drawable.drink_white_wine_bottle_1),
    WHITE_WINE_BOTTLE_2(Res.drawable.drink_white_wine_bottle_2),
    WHITE_WINE_BOTTLE_3(Res.drawable.drink_white_wine_bottle_3),
    CHAMPAGNE_BOTTLE_1(Res.drawable.drink_champagne_bottle_1),
    CHAMPAGNE_BOTTLE_2(Res.drawable.drink_champagne_bottle_2),
    CAT_COCKTAILS(Res.drawable.cat_cocktails),
    GENERIC_DRINK(Res.drawable.drink_generic_drink),
    MARTINI_1(Res.drawable.drink_martini_1),
    DAIQUIRI_1(Res.drawable.drink_daiquiri_1),
    COSMOPOLITAN_1(Res.drawable.drink_cosmopolitan_1),
    MARGARITA_1(Res.drawable.drink_margarita_1),
    PINA_COLADA_1(Res.drawable.drink_pina_colada_1),
    BAILEYS_GLASS_1(Res.drawable.drink_baileys_glass_1),
    GIN_TONIC_1(Res.drawable.drink_gin_tonic_1),
    MOJITO_1(Res.drawable.drink_mojito_1),
    CAIPIROSCA_1(Res.drawable.drink_caipirosca_1),
    CAIPIROSCA_2(Res.drawable.drink_caipirosca_2),
    RUM_COLA_1(Res.drawable.drink_rum_cola_1),
    ICED_TEA_1(Res.drawable.drink_iced_tea_1),
    MOCKTAIL_1(Res.drawable.drink_mocktail_1),
    CAT_PUNCHES(Res.drawable.cat_punches),
    PUNCH_1(Res.drawable.drink_punch_1),
    PUNCH_2(Res.drawable.drink_punch_2),
    MOCKTAIL_2(Res.drawable.drink_mocktail_2),
    WHITE_RUSSIAN_1(Res.drawable.drink_white_russian_1),
    BLOODY_MARY_1(Res.drawable.drink_bloody_mary_1),
    CAT_SPIRITS(Res.drawable.cat_spirits),
    COGNAC_1(Res.drawable.drink_cognac_1),
    WHISKY_1(Res.drawable.drink_whisky_1),
    WHISKY_2(Res.drawable.drink_whisky_2),
    WHISKY_3(Res.drawable.drink_whisky_3),
    RUM_1(Res.drawable.drink_rum_1),
    VODKA_1(Res.drawable.drink_vodka_1),
    TEQUILA_1(Res.drawable.drink_tequila_1),
    LIQUEUR_3(Res.drawable.drink_liqueur_3),
    HOT_SHOT_1(Res.drawable.drink_hot_shot_1),
    ABSINTHE_1(Res.drawable.drink_absinthe_1),
    SAKE_GLASS_1(Res.drawable.drink_sake_glass_1),
    LIQUEUR_1(Res.drawable.drink_liqueur_1),
    LIQUEUR_2(Res.drawable.drink_liqueur_2),
    GIN_1(Res.drawable.drink_gin_1),
    CAT_WARM_DRINKS(Res.drawable.cat_warm_drinks),
    IRISH_COFFEE_1(Res.drawable.drink_irish_coffee_1),
    MULLED_WINE_1(Res.drawable.drink_mulled_wine_1),
    VODKA_BOTTLE_1(Res.drawable.drink_vodka_bottle_1),
    VODKA_BOTTLE_2(Res.drawable.drink_vodka_bottle_2),
    WHISKY_BOTTLE_1(Res.drawable.drink_whisky_bottle_1),
    RUM_BOTTLE_1(Res.drawable.drink_rum_bottle_1),
    GIN_BOTTLE_1(Res.drawable.drink_gin_bottle_1),
    CREAM_LIQUEUR_BOTTLE_1(Res.drawable.drink_cream_liqueur_bottle_1),
    POISON_1(Res.drawable.drink_poison_1),
    CAT_SPECIALITY(Res.drawable.cat_speciality),
    SAKE_BOTTLE_1(Res.drawable.drink_sake_bottle_1),
    CAT_LOW_ALCOHOL(Res.drawable.cat_low_alcohol),
    WATER_GLASS_1(Res.drawable.drink_water_glass_1),
    CAT_UNCATEGORIZED(Res.drawable.cat_uncategorized),
    CAT_ALL(Res.drawable.cat_all),
    ;

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun painter(): Painter = painterResource(this.drawable)

    companion object {
        fun forName(name: String): DrinkImage = try {
            DrinkImage.valueOf(name)
        } catch (e: IllegalArgumentException) {
            logger.error("No drink icon found with name $name. Using a generic icon")
            GENERIC_DRINK
        }
    }
}

val DrinkImagesList = DrinkImage.entries.toList()


fun Image.toDrinkImage(): DrinkImage {
    return if (this is DrinkImage) this else DrinkImage.GENERIC_DRINK
}
