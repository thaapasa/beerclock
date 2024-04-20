package fi.tuska.beerclock.ui.layout

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp

data class ScreenSizeInfo(val heightPx: Int, val widthPx: Int, val heightDp: Dp, val widthDp: Dp)

@Composable
expect fun getScreenSizeInfo(): ScreenSizeInfo
