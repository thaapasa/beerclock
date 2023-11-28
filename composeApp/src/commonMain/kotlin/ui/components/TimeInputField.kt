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
import kotlinx.datetime.LocalTime

fun LocalTime.toDisplayString() = strings.time(this)


@Composable
fun TimeInputField(
    value: LocalTime,
    onValueChange: (time: LocalTime) -> Unit,
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
                painter = AppIcon.CLOCK.painter(),
                contentDescription = labelText ?: strings.pickTime,
                modifier = Modifier.size(16.dp)
            )
        }
    )
}