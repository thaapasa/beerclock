package fi.tuska.beerclock.images

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import fi.tuska.beerclock.logging.getLogger
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

private val logger = getLogger("DrinkImage")

enum class DrinkImage(val path: String) : Image {
    GENERIC_DRINK("drawable/drinks/generic_drink.webp"),
    BEER_GLASS_1("drawable/drinks/beer_glass_1.webp"),
    BEER_GLASS_2("drawable/drinks/beer_glass_2.webp"),
    BEER_GLASS_3("drawable/drinks/beer_glass_3.webp"),
    BEER_GLASS_4("drawable/drinks/beer_glass_4.webp"),
    BEER_BOTTLE_1("drawable/drinks/beer_bottle_1.webp"),
    BEER_CAN_1("drawable/drinks/beer_can_1.webp"),
    BEER_CAN_2("drawable/drinks/beer_can_2.webp"),
    BEER_CAN_3("drawable/drinks/beer_can_3.webp"),
    BEER_CAN_4("drawable/drinks/beer_can_4.webp"),
    CIDER_GLASS_1("drawable/drinks/cider_glass_1.webp"),
    CIDER_BOTTLE_1("drawable/drinks/cider_bottle_1.webp"),
    RED_WINE_GLASS_1("drawable/drinks/red_wine_glass_1.webp"),
    RED_WINE_BOTTLE_1("drawable/drinks/red_wine_bottle_1.webp"),
    RED_WINE_BOTTLE_2("drawable/drinks/red_wine_bottle_2.webp"),
    WHITE_WINE_GLASS_1("drawable/drinks/white_wine_glass_1.webp"),
    WHITE_WINE_GLASS_2("drawable/drinks/white_wine_glass_2.webp"),
    CHAMPAGNE_GLASS_1("drawable/drinks/champagne_glass_1.webp"),
    WHITE_WINE_BOTTLE_1("drawable/drinks/white_wine_bottle_1.webp"),
    WHITE_WINE_BOTTLE_2("drawable/drinks/white_wine_bottle_2.webp"),
    CHAMPAGNE_BOTTLE_1("drawable/drinks/champagne_bottle_1.webp"),
    COGNAC_1("drawable/drinks/cognac_1.webp"),
    RUM_1("drawable/drinks/rum_1.webp"),
    VODKA_1("drawable/drinks/vodka_1.webp"),
    VODKA_BOTTLE_1("drawable/drinks/vodka_bottle_1.webp"),
    CAIPIROSCA_1("drawable/drinks/caipirosca_1.webp"),
    CAIPIROSCA_2("drawable/drinks/caipirosca_2.webp"),
    MOJITO_1("drawable/drinks/mojito_1.webp"),
    MARTINI_1("drawable/drinks/martini_1.webp"),
    BLOODY_MARY_1("drawable/drinks/bloody_mary_1.webp"),
    MARGARITA_1("drawable/drinks/margarita_1.webp"),
    PINA_COLADA_1("drawable/drinks/pina_colada_1.webp"),
    WHITE_RUSSIAN_1("drawable/drinks/white_russian_1.webp"),
    IRISH_COFFEE_1("drawable/drinks/irish_coffee_1.webp"),
    MULLED_WINE_1("drawable/drinks/mulled_wine_1.webp"),
    SAKE_GLASS_1("drawable/drinks/sake_glass_1.webp"),
    SAKE_BOTTLE_1("drawable/drinks/sake_bottle_1.webp"),
    ABSINTHE_1("drawable/drinks/absinthe_1.webp"),
    WATER_GLASS_1("drawable/drinks/water_glass_1.webp"),
    MOCKTAIL_1("drawable/drinks/mocktail_1.webp"),
    MOCKTAIL_2("drawable/drinks/mocktail_2.webp"),
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
