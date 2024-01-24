package fi.tuska.beerclock.images

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import fi.tuska.beerclock.logging.getLogger
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

private val logger = getLogger("DrinkImage")

enum class DrinkImage(val path: String) : Image {
    BEER_GLASS_1("drawable/drinks/beer_glass_1.webp"),
    BEER_GLASS_2("drawable/drinks/beer_glass_2.webp"),
    BEER_STEIN_1("drawable/drinks/beer_stein_1.webp"),
    BEER_GLASS_5("drawable/drinks/beer_glass_5.webp"),
    CIDER_GLASS_1("drawable/drinks/cider_glass_1.webp"),
    SHANDY_1("drawable/drinks/shandy_1.webp"),
    MEAD_1("drawable/drinks/mead_1.webp"),
    BEER_GLASS_3("drawable/drinks/beer_glass_3.webp"),
    BEER_GLASS_4("drawable/drinks/beer_glass_4.webp"),
    BEER_BOTTLE_1("drawable/drinks/beer_bottle_1.webp"),
    BEER_BOTTLE_2("drawable/drinks/beer_bottle_2.webp"),
    BEER_BOTTLE_3("drawable/drinks/beer_bottle_3.webp"),
    CIDER_BOTTLE_1("drawable/drinks/cider_bottle_1.webp"),
    MIXED_DRINK_BOTTLE_1("drawable/drinks/mixed_drink_bottle_1.webp"),
    BEER_CAN_1("drawable/drinks/beer_can_1.webp"),
    BEER_CAN_2("drawable/drinks/beer_can_2.webp"),
    BEER_CAN_3("drawable/drinks/beer_can_3.webp"),
    BEER_CAN_4("drawable/drinks/beer_can_4.webp"),
    BEER_CAN_5("drawable/drinks/beer_can_5.webp"),
    BEER_CAN_6("drawable/drinks/beer_can_6.webp"),
    LONKERO_1("drawable/drinks/lonkero_1.webp"),
    RED_WINE_GLASS_1("drawable/drinks/red_wine_glass_1.webp"),
    WHITE_WINE_GLASS_1("drawable/drinks/white_wine_glass_1.webp"),
    WHITE_WINE_GLASS_2("drawable/drinks/white_wine_glass_2.webp"),
    CHAMPAGNE_GLASS_1("drawable/drinks/champagne_glass_1.webp"),
    SPRITZER_1("drawable/drinks/spritzer_1.webp"),
    DESSERT_WINE_1("drawable/drinks/dessert_wine_1.webp"),
    DESSERT_WINE_2("drawable/drinks/dessert_wine_2.webp"),
    RED_WINE_BOTTLE_1("drawable/drinks/red_wine_bottle_1.webp"),
    RED_WINE_BOTTLE_2("drawable/drinks/red_wine_bottle_2.webp"),
    WHITE_WINE_BOTTLE_1("drawable/drinks/white_wine_bottle_1.webp"),
    WHITE_WINE_BOTTLE_2("drawable/drinks/white_wine_bottle_2.webp"),
    WHITE_WINE_BOTTLE_3("drawable/drinks/white_wine_bottle_3.webp"),
    CHAMPAGNE_BOTTLE_1("drawable/drinks/champagne_bottle_1.webp"),
    CHAMPAGNE_BOTTLE_2("drawable/drinks/champagne_bottle_2.webp"),
    GENERIC_DRINK("drawable/drinks/generic_drink.webp"),
    MARTINI_1("drawable/drinks/martini_1.webp"),
    DAIQUIRI_1("drawable/drinks/daiquiri_1.webp"),
    COSMOPOLITAN_1("drawable/drinks/cosmopolitan_1.webp"),
    MARGARITA_1("drawable/drinks/margarita_1.webp"),
    PINA_COLADA_1("drawable/drinks/pina_colada_1.webp"),
    GIN_TONIC_1("drawable/drinks/gin_tonic_1.webp"),
    MOJITO_1("drawable/drinks/mojito_1.webp"),
    CAIPIROSCA_1("drawable/drinks/caipirosca_1.webp"),
    CAIPIROSCA_2("drawable/drinks/caipirosca_2.webp"),
    RUM_COLA_1("drawable/drinks/rum_cola_1.webp"),
    ICED_TEA_1("drawable/drinks/iced_tea_1.webp"),
    MOCKTAIL_1("drawable/drinks/mocktail_1.webp"),
    PUNCH_1("drawable/drinks/punch_1.webp"),
    PUNCH_2("drawable/drinks/punch_2.webp"),
    MOCKTAIL_2("drawable/drinks/mocktail_2.webp"),
    WHITE_RUSSIAN_1("drawable/drinks/white_russian_1.webp"),
    BLOODY_MARY_1("drawable/drinks/bloody_mary_1.webp"),
    COGNAC_1("drawable/drinks/cognac_1.webp"),
    WHISKY_1("drawable/drinks/whisky_1.webp"),
    WHISKY_2("drawable/drinks/whisky_2.webp"),
    WHISKY_3("drawable/drinks/whisky_3.webp"),
    RUM_1("drawable/drinks/rum_1.webp"),
    VODKA_1("drawable/drinks/vodka_1.webp"),
    TEQUILA_1("drawable/drinks/tequila_1.webp"),
    LIQUEUR_3("drawable/drinks/liqueur_3.webp"),
    HOT_SHOT_1("drawable/drinks/hot_shot_1.webp"),
    ABSINTHE_1("drawable/drinks/absinthe_1.webp"),
    SAKE_GLASS_1("drawable/drinks/sake_glass_1.webp"),
    LIQUEUR_1("drawable/drinks/liqueur_1.webp"),
    LIQUEUR_2("drawable/drinks/liqueur_2.webp"),
    GIN_1("drawable/drinks/gin_1.webp"),
    IRISH_COFFEE_1("drawable/drinks/irish_coffee_1.webp"),
    MULLED_WINE_1("drawable/drinks/mulled_wine_1.webp"),
    VODKA_BOTTLE_1("drawable/drinks/vodka_bottle_1.webp"),
    VODKA_BOTTLE_2("drawable/drinks/vodka_bottle_2.webp"),
    WHISKY_BOTTLE_1("drawable/drinks/whisky_bottle_1.webp"),
    RUM_BOTTLE_1("drawable/drinks/rum_bottle_1.webp"),
    GIN_BOTTLE_1("drawable/drinks/gin_bottle_1.webp"),
    POISON_1("drawable/drinks/poison_1.webp"),
    SAKE_BOTTLE_1("drawable/drinks/sake_bottle_1.webp"),
    WATER_GLASS_1("drawable/drinks/water_glass_1.webp"),
    ;

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun painter(): Painter = painterResource(this.path)

    companion object {
        fun forName(name: String): DrinkImage = try {
            DrinkImage.valueOf(name)
        } catch (e: IllegalArgumentException) {
            logger.error("No drink icon found with name $name. Using a generic icon")
            GENERIC_DRINK
        }
    }
}
