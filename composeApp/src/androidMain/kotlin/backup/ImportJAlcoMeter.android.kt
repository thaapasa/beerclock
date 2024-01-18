package fi.tuska.beerclock.backup

import android.content.Context
import android.net.Uri
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import fi.tuska.beerclock.backup.jalcometer.importJAlcometerData
import fi.tuska.beerclock.logging.getLogger
import fi.tuska.beerclock.ui.composables.ViewModel
import fi.tuska.beerclock.ui.composables.rememberWithDispose
import io.requery.android.database.sqlite.SQLiteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
) {
    val context = LocalContext.current
    val vm = rememberWithDispose { ImportJAlkaMetriViewModel(context) }
    FilePicker(
        onFilePicked = { file -> file?.let(vm::import) },
    ) { onClick -> Button(onClick = onClick, enabled = !vm.importing) { Text(title) } }
}

class ImportJAlkaMetriViewModel(private val context: Context) : ViewModel(), KoinComponent {
    var importing by mutableStateOf(false)
        private set
    
    fun import(file: Uri) {
        if (importing) return
        importing = true
        launch(Dispatchers.IO) {
            try {
                importJAlcoMeterBackupData(context, file)
            } finally {
                importing = false
            }
        }
    }

    private fun importJAlcoMeterBackupData(context: Context, file: Uri) {
        // We need to import the file in the app's own directory so that we can read it with SQLite
        val importedFile = File(context.filesDir, "jalcometer-import.db")
        if (!file.copyTo(context, importedFile)) {
            return
        }
        try {
            importJAlcoMeterDB(context, importedFile.path)
        } finally {
            when (importedFile.delete()) {
                true -> logger.debug("Deleted temporary import file $importedFile")
                false -> logger.warn("Could not delete temporary import file $importedFile")
            }
        }
    }

    private fun importJAlcoMeterDB(context: Context, filePath: String) {
        logger.info("Loading imported jAlcoMeter DB from $filePath")
        SQLiteDatabase.openDatabase(
            filePath,
            null,
            SQLiteDatabase.OPEN_READONLY
        ).use(::importJAlcometerData)
    }

}
