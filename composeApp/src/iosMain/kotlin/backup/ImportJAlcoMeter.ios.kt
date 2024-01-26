package fi.tuska.beerclock.backup

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

actual fun isJAlcoMeterImportSupported(): Boolean {
    return false
}

@Composable
actual fun ImportJAlkaMetriDataButton(
    title: String,
    modifier: Modifier,
    snackbar: SnackbarHostState,
) {
    // Can't import on iOS
}

