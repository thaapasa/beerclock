package fi.tuska.beerclock.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.dp
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.UIKit.UIApplication

@OptIn(ExperimentalForeignApi::class)
actual fun getFullscreenDialogSafeAreaPadding(): PaddingValues {
    // On iOS the top notch needs to be counted for separately
    val window = UIApplication.sharedApplication.keyWindow
    val insets = window?.safeAreaInsets ?: return PaddingValues(0.dp, 0.dp, 0.dp, 0.dp)

    insets.useContents {
        return PaddingValues(
            top = top.dp,
            bottom = bottom.dp,
            start = left.dp,
            end = right.dp
        )
    }
}
