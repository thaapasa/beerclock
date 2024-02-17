package fi.tuska.beerclock.backup.data

import android.content.Context
import android.net.Uri
import androidx.compose.material3.SnackbarHostState
import fi.tuska.beerclock.backup.copyTo
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.logging.getLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.koin.core.component.KoinComponent

private val logger = getLogger("ImportData")

class ImportDataViewModel(
    context: Context,
    snackbar: SnackbarHostState,
) : ImportExportViewModel(context, snackbar), KoinComponent {

    fun import(sourceFile: Uri) {
        if (processing) return
        processing = true
        val strings = Strings.get()
        launch(Dispatchers.IO) {
            try {
                val startTime = Clock.System.now()
                val target = getDbFile() ?: return@launch
                val displayName = getDisplayName(sourceFile)
                val importInfo = verifyBackupFile(sourceFile)
                logger.info("Importing $sourceFile ($displayName) to $target")
                val bytes = sourceFile.copyTo(context, target)
                val delta = Clock.System.now() - startTime
                logger.info("Imported DB file ($bytes bytes) in $delta")
                showMessage(
                    strings.settings.importDataMsgComplete(
                        displayName,
                        libraryDrinks = importInfo.drinkLibraryEntries,
                        records = importInfo.drinkRecordEntries
                    )
                )
            } catch (e: Exception) {
                logger.error("Error importing data file: ${e.message}")
                showMessage(strings.settings.importDataMsgError)
            } finally {
                processing = false
            }
        }
    }
}
