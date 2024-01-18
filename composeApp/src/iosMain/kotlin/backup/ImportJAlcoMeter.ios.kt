package fi.tuska.beerclock.backup

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

actual fun isJAlcoMeterImportSupported(): Boolean {
    return false
}

@Composable
actual fun ImportJAlkaMetriDataButton(title: String, modifier: Modifier) {
    // Can't import on iOS
}

