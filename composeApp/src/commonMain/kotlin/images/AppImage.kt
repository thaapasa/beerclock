package fi.tuska.beerclock.images

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

enum class AppImage(private val path: String) : Image {
    BEERCLOCK_FEATURE("drawable/images/beerclock-feature.webp");

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun painter(): Painter {
        return painterResource(this.path)
    }

}
