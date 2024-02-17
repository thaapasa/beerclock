package fi.tuska.beerclock.backup

import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import fi.tuska.beerclock.backup.data.ExportDataViewModel
import fi.tuska.beerclock.backup.data.ImportDataViewModel
import fi.tuska.beerclock.ui.composables.rememberWithDispose


actual fun isDataImportExportSupported(): Boolean {
    return true
}

@Composable
actual fun ExportDataButton(
    title: String,
    modifier: Modifier,
    snackbar: SnackbarHostState,
) {
    val context = LocalContext.current
    val vm = rememberWithDispose { ExportDataViewModel(context, snackbar) }

    FilePicker(
        onFilePicked = { file -> file?.let(vm::export) },
        mode = FileMode.CREATE,
        initialName = vm.getInitialFilename()
    ) { onClick ->
        Button(onClick = onClick, modifier = modifier, enabled = !vm.processing) {
            Text(title)
        }
    }
}

@Composable
actual fun ImportDataButton(
    title: String,
    modifier: Modifier,
    snackbar: SnackbarHostState,
) {
    val context = LocalContext.current
    val vm = rememberWithDispose { ImportDataViewModel(context, snackbar) }

    FilePicker(
        onFilePicked = { file -> file?.let(vm::import) },
        mode = FileMode.OPEN,
    ) { onClick ->
        Button(onClick = onClick, modifier = modifier, enabled = !vm.processing) {
            Text(title)
        }
    }
}
