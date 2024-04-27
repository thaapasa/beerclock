package fi.tuska.beerclock.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import fi.tuska.beerclock.images.Image
import fi.tuska.beerclock.logging.getLogger
import fi.tuska.beerclock.ui.composables.ViewModel

private val logger = getLogger("ImagePreview")

class ImagePreviewViewModel : ViewModel() {

    private var shownImage by mutableStateOf<Image?>(null)

    fun showImage(image: Image) {
        logger.info("Viewing preview of ${image.name}")
        this.shownImage = image
    }

    fun hide() {
        this.shownImage = null
    }

    @Composable
    fun Content() {
        shownImage?.let {
            PreviewImageDialog(it) { shownImage = null }
        }
    }
}

@Composable
fun PreviewImageDialog(image: Image, onClose: () -> Unit) {
    Dialog(onDismissRequest = onClose, properties = FullScreenDialogProperties) {
        Surface(modifier = Modifier.wrapContentSize()) {
            Image(
                image.painter(),
                contentDescription = "",
                modifier = Modifier.fillMaxWidth().aspectRatio(1f).clip(RoundedCornerShape(12.dp))
            )
        }
    }
}
