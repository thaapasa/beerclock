package fi.tuska.beerclock.images

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.localization.strings
import fi.tuska.beerclock.logging.getLogger
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

private val logger = getLogger("DrinkImage")

enum class DrinkImage(val path: String) {
    GENERIC_DRINK("drawable/drinks/generic_drink.webp"),
    BEER_GLASS_1("drawable/drinks/beer_glass1.webp"),
    BEER_GLASS_2("drawable/drinks/beer_glass2.webp"),
    BEER_CAN1("drawable/drinks/beer_can1.webp"),
    RED_WINE_GLASS1("drawable/drinks/red_wine_glass1.webp"),
    WHITE_WINE_GLASS1("drawable/drinks/white_wine_glass1.webp"),
    CHAMPAGNE_GLASS1("drawable/drinks/champagne_glass1.webp"),
    MARTINI("drawable/drinks/martini.webp"),
    WHISKY1("drawable/drinks/whisky1.webp");

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    fun painter(): Painter = painterResource(this.path)

    @Composable
    fun smallImage(modifier: Modifier = Modifier) {
        Image(
            painter = painter(),
            contentDescription = strings.drink.image,
            modifier = modifier.size(64.dp).clip(RoundedCornerShape(12.dp)),
        )
    }

    @Composable
    fun largeImage(modifier: Modifier = Modifier) {
        Image(
            painter = painter(),
            contentDescription = strings.drink.image,
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
