package fi.tuska.beerclock.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.localization.strings
import fi.tuska.beerclock.settings.Gender
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun GenderSelector(
    selectedValue: Gender,
    onSelectGender: (gender: Gender) -> Unit,
    modifier: Modifier = Modifier
) {
    val options = Gender.values().toList()
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier
    ) {
        TextField(
            value = strings.forGender(selectedValue),
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            placeholder = { Text(text = strings.settings.genderLabel) },
            label = { Text(text = strings.settings.genderLabel) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier.menuAnchor().fillMaxWidth()
        )
        ExposedDropdownMenu(expanded = expanded,
            modifier = Modifier.fillMaxWidth(),
            onDismissRequest = { expanded = false })
        {
            options.forEach { g ->
                DropdownMenuItem(
                    onClick = {
                        onSelectGender(g)
                        expanded = false
                    },
                    leadingIcon = {
                        Icon(
                            painter = AppIcon.forGender(g).painter(),
                            contentDescription = strings.forGender(g)
                        )
                    },
                    text = { Text(strings.forGender(g)) },
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}