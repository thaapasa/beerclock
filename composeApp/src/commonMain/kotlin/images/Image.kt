package fi.tuska.beerclock.images

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.localization.Strings

interface Image {
    @Composable
    fun painter(): Painter

    val name: String
}

@Composable
fun Image.smallImage(modifier: Modifier = Modifier, size: Dp = 64.dp) {
    Image(
        painter = painter(),
        contentDescription = Strings.get().drink.image,
        modifier = Modifier.size(size).clip(RoundedCornerShape(12.dp)).then(modifier),
    )
}


@Composable
fun Image.image(modifier: Modifier = Modifier) {
    Image(
        painter = painter(),
        contentDescription = Strings.get().drink.image,
        modifier = modifier,
    )
}


@Composable
fun Image.largeImage(modifier: Modifier = Modifier) {
    Image(
        painter = painter(),
        contentDescription = Strings.get().drink.image,
        modifier = modifier.size(128.dp).clip(RoundedCornerShape(12.dp)),
    )
}
