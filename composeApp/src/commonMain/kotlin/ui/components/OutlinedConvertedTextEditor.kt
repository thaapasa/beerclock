package fi.tuska.beerclock.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import fi.tuska.beerclock.localization.Strings

@Composable
fun <T> OutlinedConvertedTextEditor(
    value: T,
    onValueChange: (value: T) -> Unit,
    valueToString: (value: T) -> String,
    valueFromString: (text: String) -> T?,
    leadingIcon: @Composable (() -> Unit)? = null,
    label: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    modifier: Modifier = Modifier,
    errorText: String? = null,
) {
    val strings = Strings.get()
    var textState by remember { mutableStateOf(TextFieldValue(valueToString(value))) }
    // Update internal state when value from outside changes
    LaunchedEffect(value) {
        val curValue = valueFromString(textState.text)
        if (curValue != null && curValue != value) {
            val valueStr = valueToString(value)
            textState = TextFieldValue(valueStr)
        }
    }
    val isValidInput = textState.text.isEmpty() || valueFromString(textState.text) != null

    OutlinedTextField(
        label = label,
        modifier = modifier,
        value = textState,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        maxLines = 1,
        onValueChange = {
            // Update the state only for valid decimal numbers
            textState = it

            if (it.text.isNotEmpty()) {
                valueFromString(it.text)?.let(onValueChange)
            }
        },
        keyboardOptions = keyboardOptions,
        singleLine = true,
        supportingText = {
            if (isValidInput) supportingText?.invoke() else Text(
                errorText ?: strings.errors.invalidDecimal
            )
        },
        isError = !isValidInput
    )
}
