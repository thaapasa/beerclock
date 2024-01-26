package fi.tuska.beerclock.backup

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

expect fun isDataImportExportSupported(): Boolean

@Composable
expect fun ExportDataButton(
    title: String,
    modifier: Modifier = Modifier,
    snackbar: SnackbarHostState,
)

@Composable
expect fun ImportDataButton(
    title: String,
    modifier: Modifier = Modifier,
    snackbar: SnackbarHostState,
)
