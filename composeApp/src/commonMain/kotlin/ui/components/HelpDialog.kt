package fi.tuska.beerclock.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import fi.tuska.beerclock.images.AppIcon

data class HelpText(val title: String, val text: String)

@Composable
fun HelpDialog(text: HelpText, onClose: () -> Unit) {
    Dialog(onDismissRequest = onClose) {
        Surface(
            color = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.clip(RoundedCornerShape(16.dp))
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 8.dp)
            ) {
                DialogHeader(
                    text.title,
                    textStyle = MaterialTheme.typography.titleMedium,
                    height = 40.dp,
                    trailingIcon = { modifier ->
                        AppIcon.CLOSE.icon(
                            modifier = modifier.clickable(onClick = onClose)
                        )
                    }
                )
                Divider(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp))
                Text(
                    text.text,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify
                )
            }
        }
    }
}

@Composable
fun HelpButton(
    text: HelpText,
    content: @Composable (onClick: () -> Unit) -> Unit,
) {
    var open by remember { mutableStateOf(false) }
    content { open = !open }
    if (open) {
        HelpDialog(text, onClose = { open = false })
    }
}
