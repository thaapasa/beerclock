package fi.tuska.beerclock.ui.layout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp

data class ScreenSizeInfo(val heightPx: Int, val widthPx: Int, val heightDp: Dp, val widthDp: Dp)

@Composable
fun getAvailableScreenSize(): ScreenSizeInfo {
    val density = LocalDensity.current
    val config = LocalWindowInfo.current.containerSize

    return remember(density, config) {
        ScreenSizeInfo(
            heightPx = config.height,
            widthPx = config.width,
            heightDp = with(density) { config.height.toDp() },
            widthDp = with(density) { config.width.toDp() }
        )
    }
}
