package fi.tuska.beerclock.backup.data

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import fi.tuska.beerclock.backup.copyToUri
import fi.tuska.beerclock.backup.processLocally
import fi.tuska.beerclock.database.processSQLiteDatabase
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.logging.getLogger
import fi.tuska.beerclock.ui.composables.SnackbarViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.koin.core.component.KoinComponent
import java.io.File


private val logger = getLogger("ExportData")

data class ExportDataInfo(val drinkLibraryEntries: Long, val drinkRecordEntries: Long)

class ExportDataViewModel(
    private val context: Context,
    snackbar: SnackbarHostState,
) : SnackbarViewModel(snackbar), KoinComponent {
    var importing by mutableStateOf(false)
        private set

    fun export(targetFile: Uri) {
        if (importing) return
        importing = true
        val strings = Strings.get()
        launch(Dispatchers.IO) {
            try {
                val startTime = Clock.System.now()
                val source = getDbFile() ?: return@launch
                val displayName = getDisplayName(targetFile)
                logger.info("Exporting $source to $targetFile ($displayName)")
                val bytes = source.copyToUri(context, targetFile)
                val exportInfo = verifyExportedFile(targetFile)
                val delta = Clock.System.now() - startTime
                logger.info("Exported $bytes bytes in $delta: ${exportInfo.drinkLibraryEntries} drinks in library, ${exportInfo.drinkRecordEntries} records")
                showMessage(
                    strings.settings.exportDataMsgComplete(
                        displayName,
                        libraryDrinks = exportInfo.drinkLibraryEntries,
                        records = exportInfo.drinkRecordEntries
                    )
                )
            } catch (e: Exception) {
                logger.error("Error exporting data file: ${e.message}")
                showMessage(strings.settings.exportDataMsgError)
            } finally {
                importing = false
            }
        }
    }

    private fun verifyExportedFile(targetFile: Uri): ExportDataInfo =
        targetFile.processLocally(context) { importedFile ->
            processSQLiteDatabase(importedFile) { db ->
                val nLib = db.queryNumEntries("drinkLibrary")
                val nRec = db.queryNumEntries("drinkRecord")
                ExportDataInfo(drinkLibraryEntries = nLib, drinkRecordEntries = nRec)
            }
        }


    fun getInitialFilename(): String {
        return "BeerDatabase.db"
    }

    private fun getDbFile(): File? {
        val dbFile = context.getDatabasePath("beerdatabase.db");
        if (!dbFile.exists()) {
            logger.warn("Cannot locate database file at $dbFile")
            return null
        }
        return dbFile
    }

    private fun getDisplayName(uri: Uri): String? {
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
