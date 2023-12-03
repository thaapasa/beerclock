package fi.tuska.beerclock.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.localization.Strings
import kotlinx.datetime.LocalTime


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeInputField(
    value: LocalTime,
    supportingText: @Composable (() -> Unit)? = null,
    onValueChange: (time: LocalTime) -> Unit,
    modifier: Modifier = Modifier,
    labelText: String? = null
) {
    val strings = Strings.get()
    var pickerShown by remember { mutableStateOf(false) }
    var state = rememberTimePickerState(value.hour, value.minute, true)
    LaunchedEffect(value) {
        state = TimePickerState(value.hour, value.minute, true)
    }

    OutlinedTextField(
        value = strings.time(value),
        onValueChange = {},
        modifier = modifier.onFocusChanged {
            if (it.hasFocus) {
                pickerShown = true
            }
        },
        label = { labelText?.let { Text(it) } },
        supportingText = supportingText,
        readOnly = true,
        leadingIcon = {
            IconButton({ pickerShown = true }) {
                Icon(
                    painter = AppIcon.CLOCK.painter(),
                    contentDescription = labelText ?: strings.pickTime,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    )
    if (pickerShown) {
        DatePickerDialog(
            onDismissRequest = { pickerShown = false },
            confirmButton = {
                TextButton({
                    onValueChange(LocalTime(state.hour, state.minute, 0))
                    pickerShown = false
                }) { Text(strings.pickTime) }
            },
        )
        {
            TimePicker(
                state = state,
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(16.dp),
                layoutType = TimePickerLayoutType.Vertical
            )
        }
    }
}