package fi.tuska.beerclock.backup.data

import android.content.Context
import android.net.Uri
import androidx.compose.material3.SnackbarHostState
import fi.tuska.beerclock.backup.copyToUri
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.logging.getLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import kotlin.time.Clock
import kotlin.time.ExperimentalTime


private val logger = getLogger("ExportData")

@OptIn(ExperimentalTime::class)
class ExportDataViewModel(
    context: Context,
    snackbar: SnackbarHostState,
) : ImportExportViewModel(context, snackbar), KoinComponent {

    fun export(targetFile: Uri) {
        if (processing) return
        processing = true
        val strings = Strings.get()
        launch(Dispatchers.IO) {
            try {
                val startTime = Clock.System.now()
                val source = getDbFile() ?: return@launch
                val displayName = getDisplayName(targetFile)
                logger.info("Exporting $source to $targetFile ($displayName)")
                val bytes = source.copyToUri(context, targetFile)
                val exportInfo = verifyBackupFile(targetFile)
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
                processing = false
            }
        }
    }

    fun getInitialFilename(): String {
        return "BeerDatabase.db"
    }

}
