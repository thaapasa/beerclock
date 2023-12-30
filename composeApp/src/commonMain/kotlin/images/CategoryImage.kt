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

private val logger = getLogger("CategoryImage")

enum class CategoryImage(val path: String) {
    CAT_BEERS("drawable/categories/cat_beer.webp"),
    CAT_CIDERS("drawable/categories/cat_ciders.webp"),
    CAT_WINES("drawable/categories/cat_wines.webp"),
    CAT_COCKTAILS("drawable/categories/cat_cocktails.webp"),
    CAT_SPIRITS("drawable/categories/cat_spirits.webp"),
    CAT_WARM_DRINKS("drawable/categories/cat_warm_drinks.webp"),
    CAT_SPECIALITY("drawable/categories/cat_speciality.webp"),
    CAT_LOW_ALCOHOL("drawable/categories/cat_low_alcohol.webp");

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
        fun forName(name: String): CategoryImage = try {
            CategoryImage.valueOf(name)
        } catch (e: IllegalArgumentException) {
            logger.error("No drink icon found with name $name. Using a generic icon")
            CAT_BEERS
        }
    }
}
