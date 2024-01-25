package fi.tuska.beerclock.screens.settings

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.backup.ExportDataButton
import fi.tuska.beerclock.backup.ImportDataButton
import fi.tuska.beerclock.backup.ImportJAlkaMetriDataButton
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.ui.components.FormGroup

@Composable
internal fun ColumnScope.DataImportSettings(vm: SettingsViewModel) {
    val strings = Strings.get()
    val iconColor = MaterialTheme.colorScheme.onSurfaceVariant


    if (vm.canImportExportDb) {
        FormGroup(
            groupIcon = { AppIcon.SAVE_AS.icon(tint = iconColor) },
            modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp)
        ) {
            Text(strings.settings.importExportTitle)
            strings.settings.importExportDescription.forEach {
                Spacer(modifier = Modifier.height(4.dp))
                Text(it, style = MaterialTheme.typography.bodySmall)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                ExportDataButton(
                    title = strings.settings.exportDb,
                    snackbar = vm.snackbar,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                ImportDataButton(
                    title = strings.settings.importDb,
                    snackbar = vm.snackbar,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
    if (vm.canImportFromJAlcoMeter) {
        FormGroup(
            groupIcon = { AppIcon.INPUT.icon(tint = iconColor) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(strings.settings.importJAlcoMeterTitle)
            strings.settings.importJAlcoMeterDescriptions.forEach {
                Spacer(modifier = Modifier.height(4.dp))
                Text(it, style = MaterialTheme.typography.bodySmall)
            }
            Spacer(modifier = Modifier.height(16.dp))
            ImportJAlkaMetriDataButton(
                title = strings.settings.importJAlcoMeterData,
                snackbar = vm.snackbar,
            )
        }
    }
}
