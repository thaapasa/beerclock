package fi.tuska.beerclock.backup.data

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import fi.tuska.beerclock.backup.processLocally
import fi.tuska.beerclock.database.processSQLiteDatabase
import fi.tuska.beerclock.logging.getLogger
import fi.tuska.beerclock.ui.composables.SnackbarViewModel
import org.koin.core.component.KoinComponent
import java.io.File

data class BackupDataInfo(val drinkLibraryEntries: Long, val drinkRecordEntries: Long)

private val logger = getLogger("ImportExport")

open class ImportExportViewModel(val context: Context, snackbar: SnackbarHostState) :
    SnackbarViewModel(snackbar), KoinComponent {
    var processing by mutableStateOf(false)
        protected set

    protected fun verifyBackupFile(backupFile: Uri): BackupDataInfo =
        backupFile.processLocally(context) { file ->
            processSQLiteDatabase(file) { db ->
                val nLib = db.queryNumEntries("drinkLibrary")
                val nRec = db.queryNumEntries("drinkRecord")
                BackupDataInfo(drinkLibraryEntries = nLib, drinkRecordEntries = nRec)
            }
        }

    protected fun getDbFile(): File? {
        val dbFile = context.getDatabasePath("beerdatabase.db");
        if (!dbFile.exists()) {
            logger.warn("Cannot locate database file at $dbFile")
            return null
        }
        return dbFile
    }

    protected fun getDisplayName(uri: Uri): String? {
        var displayName: String? = null

        context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            // moveToFirst() returns false if the cursor has 0 rows.
            if (cursor.moveToFirst()) {
                val colIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (colIndex < 0) return null
                displayName = cursor.getString(colIndex)
            }
        }

        return displayName
    }
}