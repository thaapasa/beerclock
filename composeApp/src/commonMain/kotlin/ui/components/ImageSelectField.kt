package fi.tuska.beerclock.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.images.Image
import fi.tuska.beerclock.ui.composables.rememberWithDispose


@Composable
fun <T : Image> ImageSelectField(
    value: T,
    options: List<T>,
    titleText: String? = null,
    onValueChange: (image: T) -> Unit,
    minImageSize: Dp,
) {
    var dialogOpen by remember { mutableStateOf(false) }
    val previewImg = rememberWithDispose { ImagePreviewViewModel() }

    value.let { img ->
        Image(
            img.painter(),
            contentDescription = "",
            modifier = Modifier.size(64.dp).clip(RoundedCornerShape(12.dp))
                .combinedClickableReleasable(
                    onClick = { dialogOpen = true },
                    onLongPress = { previewImg.showImage(img) },
                    onRelease = previewImg::hide,
                )
        )
    }
    if (dialogOpen) {
        ImageSelectDialog(
            onClose = { dialogOpen = false },
            options = options,
            titleText = titleText,
            onValueChange = onValueChange,
            minImageSize = minImageSize,
            preview = previewImg,
        )
    }
    previewImg.Content()
}


@Composable
fun <T : Image> ImageSelectDialog(
    onClose: () -> Unit,
    options: List<T>,
    titleText: String? = null,
    onValueChange: (image: T) -> Unit,
    minImageSize: Dp,
    preview: ImagePreviewViewModel,
) {
    AppDialog(onClose = onClose) {
        titleText?.let {
            Text(
                titleText,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.titleMedium,
            )
            HorizontalDivider(
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                thickness = 1.dp,
                modifier = Modifier.padding(top = 2.dp, bottom = 8.dp)
            )
        }
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = minImageSize),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(options) { item ->
                Image(
                    item.painter(),
                    contentDescription = "",
                    modifier = Modifier.size(64.dp).clip(RoundedCornerShape(12.dp))
                        .combinedClickableReleasable(
                            onClick = {
                                onValueChange(item)
                                onClose()
                            },
                            onLongPress = { preview.showImage(item) },
                            onRelease = preview::hide,
                        )
                )
            }
        }
    }
}

