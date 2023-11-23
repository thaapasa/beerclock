package fi.tuska.beerclock.images

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import fi.tuska.beerclock.logging.getLogger
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

val logger = getLogger("DrinkImage")

enum class DrinkImage(val path: String) {
    BEER1("drawable/drinks/beer1.webp"),
    BEER2("drawable/drinks/beer2.webp"),
    BEER_CAN1("drawable/drinks/beer_can1.webp"),
    WHISKY1("drawable/drinks/whisky1.webp");

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    fun painter(): Painter = painterResource(this.path)

    companion object {
        fun forPath(path: String): DrinkImage = try {
            DrinkImage.valueOf(path)
        } catch (e: IllegalArgumentException) {
            logger.error("No drink icon found for $path. Using a generic icon")
            BEER1
        }

    }
}
