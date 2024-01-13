package fi.tuska.beerclock.screens.settings

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.ui.components.DecimalField
import fi.tuska.beerclock.ui.components.FieldDescription
import fi.tuska.beerclock.ui.components.FormGroup
import fi.tuska.beerclock.ui.components.TimeInputField

@Composable
internal fun ColumnScope.UserSettings(vm: SettingsViewModel) {
    val strings = Strings.get()
    val iconColor = MaterialTheme.colorScheme.onSurfaceVariant

    FormGroup(groupIcon = { AppIcon.LANGUAGE.icon(tint = iconColor) }) {
        LanguageDropdown(selected = vm.locale, onSelect = { vm.locale = it })
        FieldDescription(
            strings.settings.localeDescription,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
    Spacer(Modifier.height(16.dp))
    FormGroup(groupIcon = { AppIcon.PERSON.icon(tint = iconColor) }) {
        DecimalField(
            value = vm.weightKg,
            onValueChange = { vm.weightKg = it },
            modifier = Modifier.fillMaxWidth().padding(bottom = 0.dp),
            label = { Text(text = strings.settings.weightLabel) },
            trailingIcon = { Text(strings.settings.weightUnit) },
            supportingText = { Text(strings.settings.weightDescription) }
        )
        GenderSelector(
            selected = vm.gender,
            onSelect = { vm.gender = it },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
        )
        FieldDescription(
            strings.settings.genderDescription,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
    Spacer(Modifier.height(16.dp))
    FormGroup(groupIcon = { AppIcon.CALENDAR.icon(tint = iconColor) }) {
        TimeInputField(
            value = vm.startOfDay,
            onValueChange = { vm.startOfDay = it },
            modifier = Modifier.fillMaxWidth(),
            labelText = strings.settings.startOfDay,
            supportingText = { Text(strings.settings.startOfDayDescription) },
        )
    }
}
