package fi.tuska.beerclock.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> DropdownSelect(
    options: Array<T>,
    selected: T,
    onSelect: (value: T) -> Unit,
    modifier: Modifier = Modifier,
    valueToText: (value: T) -> String,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    iconForValue: @Composable ((value: T) -> Unit)? = null,
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = valueToText(selected),
            onValueChange = {},
            readOnly = true,
            leadingIcon = iconForValue?.let { { it(selected) } },
            trailingIcon = { TrailingIcon(expanded = expanded) },
            placeholder = placeholder,
            label = label,
            modifier = Modifier.fillMaxWidth().menuAnchor(),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            modifier = Modifier.exposedDropdownSize(matchTextFieldWidth = true),
            onDismissRequest = { expanded = false }
        )
        {
            options.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        onSelect(option)
                        expanded = false
                    },
                    leadingIcon = iconForValue?.let { { it(option) } },
                    text = { Text(valueToText(option)) },
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}