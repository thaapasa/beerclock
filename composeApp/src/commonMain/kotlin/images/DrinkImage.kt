package fi.tuska.beerclock.images

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.logging.getLogger
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

private val logger = getLogger("DrinkImage")

enum class DrinkImage(val path: String) {
    GENERIC_DRINK("drawable/drinks/generic_drink.webp"),
    BEER_GLASS_1("drawable/drinks/beer_glass_1.webp"),
    BEER_BOTTLE_1("drawable/drinks/beer_bottle_1.webp"),
    BEER_CAN_1("drawable/drinks/beer_can_1.webp"),
    BEER_CAN_2("drawable/drinks/beer_can_2.webp"),
    BEER_CAN_3("drawable/drinks/beer_can_3.webp"),
    BEER_CAN_4("drawable/drinks/beer_can_4.webp"),
    RED_WINE_GLASS_1("drawable/drinks/red_wine_glass_1.webp"),
    WHITE_WINE_GLASS_1("drawable/drinks/white_wine_glass_1.webp"),
    WHITE_WINE_GLASS_2("drawable/drinks/white_wine_glass_2.webp"),
    WHITE_WINE_BOTTLE_2("drawable/drinks/white_wine_bottle_1.webp"),
    COGNAC_1("drawable/drinks/cognac_1.webp"),
    CAIPIROSCA_1("drawable/drinks/caipirosca_1.webp"),
    CAIPIROSCA_2("drawable/drinks/caipirosca_2.webp"),
    MARTINI_1("drawable/drinks/martini_1.webp");

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    fun painter(): Painter = painterResource(this.path)

    @Composable
    fun smallImage(modifier: Modifier = Modifier) {
        Image(
            painter = painter(),
            contentDescription = Strings.get().drink.image,
            modifier = modifier.size(64.dp).clip(RoundedCornerShape(12.dp)),
        )
    }

    @Composable
    fun image(modifier: Modifier = Modifier) {
        Image(
            painter = painter(),
            contentDescription = Strings.get().drink.image,
            modifier = modifier,
        )
    }


    @Composable
    fun largeImage(modifier: Modifier = Modifier) {
        Image(
            painter = painter(),
            contentDescription = Strings.get().drink.image,
            modifier = modifier.size(128.dp).clip(RoundedCornerShape(12.dp)),
        )
    }

    companion object {
        fun forName(name: String): DrinkImage = try {
            DrinkImage.valueOf(name)
        } catch (e: IllegalArgumentException) {
            logger.error("No drink icon found with name $name. Using a generic icon")
            GENERIC_DRINK
        }
    }
}
