package fi.tuska.beerclock.backup

import android.content.Context
import android.net.Uri
import androidx.compose.material.SnackbarHostState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import fi.tuska.beerclock.backup.jalcometer.ImportStatus
import fi.tuska.beerclock.backup.jalcometer.StatusDisplay
import fi.tuska.beerclock.backup.jalcometer.importJAlcometerData
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.logging.getLogger
import fi.tuska.beerclock.ui.composables.ViewModel
import fi.tuska.beerclock.ui.composables.rememberWithDispose
import io.requery.android.database.sqlite.SQLiteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.koin.core.component.KoinComponent
import java.io.File


private val logger = getLogger("ImportJAlcoMeter")

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
    ) { onClick -> Button(onClick = onClick, enabled = !vm.importing) { Text(title) } }
    vm.Status()
}

class ImportJAlkaMetriViewModel(
    private val context: Context,
    private val snackbar: SnackbarHostState,
) : ViewModel(), KoinComponent {
    var importing by mutableStateOf(false)
        private set

    var status by mutableStateOf<ImportStatus?>(null)

    fun import(file: Uri) {
        if (importing) return
        importing = true
        val strings = Strings.get()
        launch(Dispatchers.IO) {
            try {
                status = ImportStatus(strings.settings.importMsgStarting)
                val start = Clock.System.now()
                importJAlcoMeterBackupData(context, file)
                val delta = Clock.System.now() - start
                logger.info("jAlcoMeter data import took $delta")
                status = ImportStatus(strings.settings.importMsgComplete, 1f)
                launch { snackbar.showSnackbar(strings.settings.importMsgComplete) }
            } catch (e: Exception) {
                logger.error("Error importing data from jAlcoMeter: ${e.message}")
                status = ImportStatus(strings.settings.importMsgError, 0f)
            } finally {
                importing = false
            }
        }
    }

    @Composable
    fun Status() {
        status?.let {
            StatusDisplay(it)
        }
    }

    private fun importJAlcoMeterBackupData(context: Context, file: Uri) {
        // We need to import the file in the app's own directory so that we can read it with SQLite
        val importedFile = File(context.filesDir, "jalcometer-import.db")
        if (!file.copyTo(context, importedFile)) {
            return
        }
        try {
            importJAlcoMeterDB(importedFile.path) { status = it }
        } finally {
            when (importedFile.delete()) {
                true -> logger.debug("Deleted temporary import file $importedFile")
                false -> logger.warn("Could not delete temporary import file $importedFile")
            }
        }
    }

    private fun importJAlcoMeterDB(
        filePath: String,
        showStatus: (mgs: ImportStatus) -> Unit,
    ) {
        logger.info("Loading imported jAlcoMeter DB from $filePath")
        SQLiteDatabase.openDatabase(
            filePath,
            null,
            SQLiteDatabase.OPEN_READONLY
        ).use { importJAlcometerData(it, showStatus) }
    }

}
