package fi.tuska.beerclock.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import fi.tuska.beerclock.drinks.BasicDrinkInfo
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.images.image
import fi.tuska.beerclock.images.largeImage

// Lifted higher to show on top of the drink list
val elevation = 24.dp


/**
 * This is the basic layout of the drink info dialog, with the drink image at the top. You can
 * click on the image to get it shown in fullscreen-width dialog, replacing the other content.
 */
@Composable
fun <T : BasicDrinkInfo> DrinkDialog(
    drink: T,
    onClose: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    val textColor = MaterialTheme.colorScheme.onSurface
    var imageShown by remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = onClose, properties = DialogProperties(
            dismissOnBackPress = true,
            usePlatformDefaultWidth = !imageShown,
        )
    ) {
        Surface(
            modifier = if (imageShown) Modifier.fillMaxWidth()
            else Modifier.wrapContentWidth().wrapContentHeight(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = elevation,
            color = MaterialTheme.colorScheme.surfaceColorAtElevation(elevation),
            contentColor = textColor
        ) {
            Column(modifier = Modifier.fillMaxWidth().padding(if (imageShown) 0.dp else 16.dp)) {
                Box(
                    modifier = Modifier.fillMaxWidth().wrapContentHeight()
                ) {
                    if (!imageShown) {
                        drink.image.largeImage(
                            modifier = Modifier.align(Alignment.Center).padding(top = 16.dp)
                                .clickable(onClick = { imageShown = !imageShown })
                        )
                    } else {
                        drink.image.image(
                            modifier = Modifier.fillMaxWidth(1f).aspectRatio(1f)
                                .align(Alignment.Center)
                                .clickable(onClick = { imageShown = !imageShown })
                        )
                    }
                    AppIcon.CLOSE.iconButton(
                        onClick = onClose,
                        modifier = Modifier.align(Alignment.TopEnd)
                    )
                }

                if (!imageShown) {
                    content()
                }
            }
        }
    }
}
