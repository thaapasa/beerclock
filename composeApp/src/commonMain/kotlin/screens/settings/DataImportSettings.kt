package fi.tuska.beerclock.screens.settings

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.backup.ImportJAlkaMetriDataButton
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.ui.components.FormGroup

@Composable
internal fun ColumnScope.DataImportSettings(vm: SettingsViewModel) {
    val strings = Strings.get()
    val iconColor = MaterialTheme.colorScheme.onSurfaceVariant

    FormGroup(groupIcon = { AppIcon.INPUT.icon(tint = iconColor) }) {
        Text(strings.settings.importJAlcoMeterTitle)
        strings.settings.importJAlcoMeterDescriptions.forEach {
            Spacer(modifier = Modifier.height(4.dp))
            Text(it, style = MaterialTheme.typography.bodySmall)
        }
        Spacer(modifier = Modifier.height(16.dp))
        ImportJAlkaMetriDataButton(
            title = strings.settings.importJAlcoMeterData,
            snackbar = vm.snackbar
        )
    }
}
