package fi.tuska.beerclock.backup

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

expect fun isJAlcoMeterImportSupported(): Boolean

@Composable
expect fun ImportJAlkaMetriDataButton(
    title: String,
    modifier: Modifier = Modifier,
    snackbar: SnackbarHostState,
)
