package fi.tuska.beerclock.backup.jalcometer

import android.content.Context
import android.net.Uri
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import fi.tuska.beerclock.backup.processLocally
import fi.tuska.beerclock.database.processSQLiteDatabase
import fi.tuska.beerclock.drinks.DrinkService
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.logging.getLogger
import fi.tuska.beerclock.ui.composables.SnackbarViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.koin.core.component.KoinComponent


private val logger = getLogger("ImportJAlcoMeter")


class ImportJAlkaMetriViewModel(
    private val context: Context,
    snackbar: SnackbarHostState,
) : SnackbarViewModel(snackbar), KoinComponent {
    var importing by mutableStateOf(false)
        private set

    private val drinkService: DrinkService = DrinkService()

    private var status by mutableStateOf<ImportStatus?>(null)

    fun import(file: Uri) {
        if (importing) return
        importing = true
        val strings = Strings.get()
        launch(Dispatchers.IO) {
            try {
                status = ImportStatus(strings.settings.importJAlcoMeterMsgStarting)
                val start = Clock.System.now()
                importJAlcoMeterBackupData(context, file)
                val delta = Clock.System.now() - start
                logger.info("jAlcoMeter data import took $delta")
                status = ImportStatus(strings.settings.importJAlcoMeterMsgComplete, 1f)
                showMessage(strings.settings.importJAlcoMeterMsgComplete)
            } catch (e: Exception) {
                logger.error("Error importing data from jAlcoMeter: ${e.message}")
                status = ImportStatus(strings.settings.importJAlcoMeterMsgError, 0f)
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
        file.processLocally(context, suffix = ".db") { importedFile ->
            logger.info("Loading imported jAlcoMeter DB from $importedFile")
            processSQLiteDatabase(importedFile) {
                importJAlcometerData(ImportContext(it, drinkService, showStatus = { status = it }))
            }
        }
    }

}
