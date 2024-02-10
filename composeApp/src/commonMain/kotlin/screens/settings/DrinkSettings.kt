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
import fi.tuska.beerclock.ui.components.FormGroup

@Composable
internal fun ColumnScope.DrinkSettings(vm: SettingsViewModel) {
    val strings = Strings.get()
    val iconColor = MaterialTheme.colorScheme.onSurfaceVariant

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
                    strings.settings.unitPermille,
                    modifier = Modifier.padding(16.dp)
                )
            }
        )
    }
    Spacer(Modifier.height(16.dp))
    FormGroup(groupIcon = { AppIcon.GAUGE.icon(tint = iconColor) }) {
        DecimalField(
            value = vm.maxBAC,
            onValueChange = { vm.maxBAC = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(strings.settings.maxBacLabel) },
            supportingText = { Text(strings.settings.maxBacDescription) },
            trailingIcon = {
                Text(
                    strings.settings.unitPermille,
                    modifier = Modifier.padding(16.dp)
                )
            }
        )
        Spacer(Modifier.height(16.dp))
        DecimalField(
            value = vm.maxDailyUnits,
            onValueChange = { vm.maxDailyUnits = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(strings.settings.maxDailyUnitsLabel) },
            supportingText = { Text(strings.settings.maxDailyUnitsDescription) },
            trailingIcon = {
                Text(
                    strings.settings.unitStandardDrinks,
                    modifier = Modifier.padding(16.dp)
                )
            }
        )
        Spacer(Modifier.height(16.dp))
        DecimalField(
            value = vm.maxWeeklyUnits,
            onValueChange = { vm.maxWeeklyUnits = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(strings.settings.maxWeeklyUnitsLabel) },
            supportingText = { Text(strings.settings.maxWeeklyUnitsDescription) },
            trailingIcon = {
                Text(
                    strings.settings.unitStandardDrinks,
                    modifier = Modifier.padding(16.dp)
                )
            }
        )
    }
}
