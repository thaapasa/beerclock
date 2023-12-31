package fi.tuska.beerclock.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.ui.components.DecimalField
import fi.tuska.beerclock.ui.components.FieldDescription
import fi.tuska.beerclock.ui.components.FormGroup
import fi.tuska.beerclock.ui.components.TimeInputField
import fi.tuska.beerclock.ui.composables.rememberWithDispose
import fi.tuska.beerclock.ui.layout.SubLayout

object SettingsScreen : Screen {

    @Composable
    override fun Content() {
        SubLayout(
            content = { innerPadding -> SettingsPage(innerPadding) },
            title = Strings.get().settings.title
        )
    }
}

@Composable
fun SettingsPage(innerPadding: PaddingValues) {
    val strings = Strings.get()
    val vm = rememberWithDispose { SettingsViewModel() }

    vm.trackChanges()
    val iconColor = MaterialTheme.colorScheme.onSurfaceVariant
    val scrollState = rememberScrollState()

    Column(
        Modifier.padding(innerPadding).padding(16.dp).fillMaxWidth().verticalScroll(scrollState),
    ) {
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
        Spacer(Modifier.height(16.dp))
        FormGroup(groupIcon = { AppIcon.DRINK.icon(tint = iconColor) }) {
            DecimalField(
                value = vm.gramsInUnit,
                onValueChange = { vm.gramsInUnit = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(strings.settings.alcoholGramsLabel) },
                supportingText = { Text(strings.settings.alcoholGramsDescription) },
                trailingIcon = {
                    Text(
                        strings.settings.alcoholGramsUnit,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            )
            Spacer(Modifier.height(16.dp))
            AlcoholUnitsDropdown(onSelect = { vm.gramsInUnit = it })
        }
        Spacer(Modifier.height(16.dp))
        FormGroup(groupIcon = { AppIcon.CAR.icon(tint = iconColor) }) {
            DecimalField(
                value = vm.drivingLimitBac,
                onValueChange = { vm.drivingLimitBac = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(strings.settings.drivingLimitBacLabel) },
                supportingText = { Text(strings.settings.drivingLimitBacDescription) },
                trailingIcon = {
                    Text(
                        strings.settings.drivingLimitBacUnit,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            )
        }
        Spacer(Modifier.height(16.dp))
    }
}
