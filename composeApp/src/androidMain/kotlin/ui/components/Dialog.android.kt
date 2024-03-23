package fi.tuska.beerclock.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.dp

actual fun getFullscreenDialogSafeAreaPadding(): PaddingValues {
    // On Android the fullscreen dialog works properly and doesn't require extra padding
    return PaddingValues(all = 0.dp)
}
