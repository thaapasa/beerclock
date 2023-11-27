package fi.tuska.beerclock.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import fi.tuska.beerclock.localization.strings

@Composable
fun DecimalField(
    value: Double,
    onValueChange: (value: Double) -> Unit,
    label: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    errorText: String? = null
) {
    var textState by remember { mutableStateOf(TextFieldValue(value.toString())) }
    val isValidInput = textState.text.isEmpty() || textState.text.toDoubleOrNull() != null

    OutlinedTextField(
        label = label,
        modifier = modifier,
        value = textState,
        trailingIcon = trailingIcon,
        onValueChange = {
            // Update the state only for valid decimal numbers
            textState = it

            if (it.text.isNotEmpty() && it.text.toDoubleOrNull() != null) {
                onValueChange(it.text.toDouble())
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        singleLine = true,
        supportingText = {
            if (isValidInput) null else Text(
                errorText ?: strings.errors.invalidDecimal
            )
        },
        isError = !isValidInput,
    )
}