package fi.tuska.beerclock.images

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import beerclock.composeapp.generated.resources.Res
import beerclock.composeapp.generated.resources.img_app_icon
import beerclock.composeapp.generated.resources.img_beerclock_feature
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
enum class AppImage(private val drawable: DrawableResource) : Image {
    APP_ICON(Res.drawable.img_app_icon),
    BEERCLOCK_FEATURE(Res.drawable.img_beerclock_feature);

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun painter(): Painter {
        return painterResource(this.drawable)
    }

}
