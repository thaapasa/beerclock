package fi.tuska.beerclock.ui.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
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
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.localization.strings
import fi.tuska.beerclock.util.fromUTCEpochMillis
import fi.tuska.beerclock.util.toUTCEpochMillis
import kotlinx.datetime.LocalDate


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerIcon(
    value: LocalDate,
    onValueChange: (date: LocalDate) -> Unit,
    modifier: Modifier = Modifier,
    icon: AppIcon = AppIcon.CALENDAR,
    title: String = strings.pickDate
) {
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
    IconButton(modifier = modifier, onClick = { pickerShown = !pickerShown }) {
        icon.icon(contentDescription = title)
    }
    if (pickerShown) {
        DatePickerDialog(
            onDismissRequest = { pickerShown = false },
            confirmButton = {
                TextButton({
                    state.selectedDateMillis?.let {
                        onValueChange(LocalDate.fromUTCEpochMillis(it))
                        pickerShown = false
                    }
                }) { Text(title) }
            })
        {
            DatePicker(
                state = state, showModeToggle = false
            )
        }
    }
}