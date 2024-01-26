package fi.tuska.beerclock.backup.data

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import fi.tuska.beerclock.backup.copyToUri
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.logging.getLogger
import fi.tuska.beerclock.ui.composables.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.koin.core.component.KoinComponent
import java.io.File


private val logger = getLogger("ExportData")


class ExportDataViewModel(
    private val context: Context,
    private val snackbar: SnackbarHostState,
) : ViewModel(), KoinComponent {
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

                val delta = Clock.System.now() - startTime
                logger.info("Exported $bytes bytes in $delta")
                launch {
                    snackbar.showSnackbar(strings.settings.exportDataMsgComplete(displayName))
                }
            } catch (e: Exception) {
                logger.error("Error exporting data file: ${e.message}")
                launch {
                    snackbar.showSnackbar(strings.settings.exportDataMsgError)
                }
            } finally {
                importing = false
            }
        }
    }

    fun getInitialFilename(): String {
        return "BeerDatabase.db"
    }

    fun getDbFile(): File? {
        val dbFile = context.getDatabasePath("beerdatabase.db");
        if (!dbFile.exists()) {
            logger.warn("Cannot locate database file at $dbFile")
            return null
        }
        return dbFile
    }

    fun getDisplayName(uri: Uri): String? {
        var displayName: String? = null

        context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            // moveToFirst() returns false if the cursor has 0 rows.
            if (cursor.moveToFirst()) {
                // Note it's called "Display Name". This is provider-specific.
                val colIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (colIndex < 0) return null
                displayName = cursor.getString(colIndex)
            }
        }

        return displayName
    }
}
