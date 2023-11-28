package fi.tuska.beerclock.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.localization.strings
import kotlinx.datetime.LocalDate

fun LocalDate.toDisplayString() = strings.date(this)


@Composable
fun DateInputField(
    value: LocalDate,
    onValueChange: (time: LocalDate) -> Unit,
    modifier: Modifier = Modifier,
    labelText: String? = null
) {
    OutlinedTextField(
        value = value.toDisplayString(),
        onValueChange = {},
        modifier = modifier,
        label = { labelText?.let { Text(it) } },
        readOnly = true,
        leadingIcon = {
            Icon(
                painter = AppIcon.CALENDAR.painter(),
                contentDescription = labelText ?: strings.pickDate,
                modifier = Modifier.size(16.dp)
            )
        }
    )
}