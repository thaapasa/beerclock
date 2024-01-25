package fi.tuska.beerclock.backup

import androidx.compose.material.SnackbarHostState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import fi.tuska.beerclock.backup.jalcometer.ImportJAlkaMetriViewModel
import fi.tuska.beerclock.ui.composables.rememberWithDispose


actual fun isJAlcoMeterImportSupported(): Boolean {
    return true
}

@Composable
actual fun ImportJAlkaMetriDataButton(
    title: String,
    modifier: Modifier,
    snackbar: SnackbarHostState,
) {
    val context = LocalContext.current
    val vm = rememberWithDispose { ImportJAlkaMetriViewModel(context, snackbar) }
    FilePicker(
        onFilePicked = { file -> file?.let(vm::import) },
    ) { onClick ->
        Button(onClick = onClick, enabled = !vm.importing, modifier = modifier) {
            Text(title)
        }
    }
    vm.Status()
}
