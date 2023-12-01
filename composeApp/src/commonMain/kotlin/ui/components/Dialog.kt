package fi.tuska.beerclock.ui.components

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

val FullScreenDialogProperties =
    DialogProperties(dismissOnBackPress = true, usePlatformDefaultWidth = false)

@Composable
fun FullScreenDialog(
    onDismissRequest: () -> Unit,
    dialogProperties: DialogProperties = FullScreenDialogProperties,
    content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = dialogProperties
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            content = content
        )
    }
}

@Composable
fun DialogHeader(
    titleText: String,
    leadingIcon: (@Composable (modifier: Modifier) -> Unit)? = null,
    textButton: (@Composable (modifier: Modifier) -> Unit)? = null,
) {
    Row(modifier = Modifier.fillMaxWidth().height(56.dp)) {
        leadingIcon?.invoke(
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        Text(
            titleText,
            modifier = Modifier.align(Alignment.CenterVertically).weight(1f),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleLarge
        )
        textButton?.invoke(
            modifier = Modifier.align(Alignment.CenterVertically).padding(end = 8.dp)
        )
    }
}