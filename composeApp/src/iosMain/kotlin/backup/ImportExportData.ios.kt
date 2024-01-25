package fi.tuska.beerclock.backup

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


actual fun isDataImportExportSupported(): Boolean {
    return false
}

@Composable
actual fun ExportDataButton(
    title: String,
    modifier: Modifier,
    snackbar: SnackbarHostState,
) {
    // Can't export on iOS
}


@Composable
actual fun ImportDataButton(
    title: String,
    modifier: Modifier,
    snackbar: SnackbarHostState,
) {
    // Can't import on iOS
}

