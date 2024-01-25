package fi.tuska.beerclock.backup

import androidx.compose.material.SnackbarHostState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import fi.tuska.beerclock.logging.getLogger
import kotlinx.coroutines.launch


private val logger = getLogger("ImportExportData")


actual fun isDataImportExportSupported(): Boolean {
    return true
}

@Composable
actual fun ExportDataButton(
    title: String,
    modifier: Modifier,
    snackbar: SnackbarHostState,
) {
    val scope = rememberCoroutineScope()
    Button(
        onClick = { scope.launch { snackbar.showSnackbar("Coming soon") } },
        modifier = modifier
    ) {
        Text(title)
    }
}

@Composable
actual fun ImportDataButton(
    title: String,
    modifier: Modifier,
    snackbar: SnackbarHostState,
) {
    val scope = rememberCoroutineScope()
    Button(
        onClick = { scope.launch { snackbar.showSnackbar("Coming soon") } },
        modifier = modifier
    ) {
        Text(title)
    }
}
