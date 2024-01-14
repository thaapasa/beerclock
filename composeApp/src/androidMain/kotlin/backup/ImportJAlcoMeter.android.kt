package fi.tuska.beerclock.backup

import android.content.Context
import android.net.Uri
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import fi.tuska.beerclock.logging.getLogger
import io.requery.android.database.sqlite.SQLiteDatabase
import java.io.File

private val logger = getLogger("JAlcoMeterImporter")

actual fun isJAlcoMeterImportSupported(): Boolean {
    return true
}

@Composable
actual fun ImportJAlkaMetriDataButton(title: String, modifier: Modifier) {
    val context = LocalContext.current
    FilePicker(
        onFilePicked = { file ->
            logger.info("Picked $file")
            file?.let { importJAlcoMeterBackupData(context, it) }
        },
    ) { onClick -> Button(onClick = onClick) { Text(title) } }
}

fun importJAlcoMeterBackupData(context: Context, file: Uri) {
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

fun importJAlcoMeterDB(context: Context, filePath: String) {
    logger.info("Loading imported jAlcoMeter DB from $filePath")
    SQLiteDatabase.openDatabase(
        filePath,
        null,
        SQLiteDatabase.OPEN_READONLY
    ).use(::importJAlcometerData)
}

fun importJAlcometerData(db: SQLiteDatabase) {
    val cats = db.queryNumEntries("categories")
    logger.info("There are $cats categories")
}
