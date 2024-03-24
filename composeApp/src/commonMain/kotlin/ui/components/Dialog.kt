package fi.tuska.beerclock.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

expect fun getFullscreenDialogSafeAreaPadding(): PaddingValues

val FullScreenDialogProperties =
    DialogProperties(
        dismissOnBackPress = true,
        usePlatformDefaultWidth = false,
    )

@Composable
fun FullScreenDialog(
    onDismissRequest: () -> Unit,
    dialogProperties: DialogProperties = FullScreenDialogProperties,
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = dialogProperties,
    ) {
        Surface(
            modifier = Modifier.padding(getFullscreenDialogSafeAreaPadding()).fillMaxSize(),
            color = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            content = content
        )
    }
}

@Composable
fun DialogHeader(
    titleText: String,
    height: Dp = 56.dp,
    leadingIcon: (@Composable (modifier: Modifier) -> Unit)? = null,
    trailingIcon: (@Composable (modifier: Modifier) -> Unit)? = null,
    textStyle: TextStyle = MaterialTheme.typography.titleLarge,
) {
    Row(modifier = Modifier.fillMaxWidth().height(height)) {
        leadingIcon?.invoke(
            Modifier.align(Alignment.CenterVertically)
        )
        Text(
            titleText,
            modifier = Modifier.align(Alignment.CenterVertically).weight(1f),
            color = MaterialTheme.colorScheme.onSurface,
            style = textStyle
        )
        trailingIcon?.invoke(
            Modifier.align(Alignment.CenterVertically)
        )
    }
}
