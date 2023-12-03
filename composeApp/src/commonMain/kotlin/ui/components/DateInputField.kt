package fi.tuska.beerclock.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.util.fromUTCEpochMillis
import fi.tuska.beerclock.util.toUTCEpochMillis
import kotlinx.datetime.LocalDate


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateInputField(
    value: LocalDate,
    onValueChange: (date: LocalDate) -> Unit,
    modifier: Modifier = Modifier,
    labelText: String? = null
) {
    val strings = Strings.get()
    var pickerShown by remember { mutableStateOf(false) }
    var state = rememberDatePickerState()
    LaunchedEffect(value) {
        state.setSelection(value.toUTCEpochMillis())
    }
    // Listen to internal state changes to auto-close dialog
    LaunchedEffect(state.selectedDateMillis) {
        // Only auto-pick date when using the Picker, and when the date changes
        if (pickerShown && state.displayMode == DisplayMode.Picker) {
            state.selectedDateMillis?.let {
                val date = LocalDate.fromUTCEpochMillis(it)
                if (date != value) {
                    onValueChange(date)
                    pickerShown = false
                }
            }
        }
    }
    OutlinedTextField(
        value = strings.date(value),
        onValueChange = {},
        modifier = modifier.onFocusChanged {
            if (it.hasFocus) {
                pickerShown = true
            }
        },
        label = { labelText?.let { Text(it) } },
        readOnly = true,
        leadingIcon = {
            IconButton({ pickerShown = true }) {
                Icon(
                    painter = AppIcon.CALENDAR.painter(),
                    contentDescription = labelText ?: strings.pickDate,
                    modifier = Modifier.size(16.dp)
                )
            }
        },
    )
    if (pickerShown) {
        DatePickerDialog(
            onDismissRequest = { pickerShown = false },
            confirmButton = {
                TextButton({
                    state.selectedDateMillis?.let {
                        onValueChange(LocalDate.fromUTCEpochMillis(it))
                        pickerShown = false
                    }
                }) { Text(strings.pickDate) }
            })
        {
            DatePicker(
                state = state, showModeToggle = false
            )
        }
    }
}