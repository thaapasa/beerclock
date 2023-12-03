package fi.tuska.beerclock.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FormGroup(
    groupIcon: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Row(modifier = modifier) {
        groupIcon?.let {
            Column(modifier = Modifier.padding(end = 24.dp, top = 8.dp)) {
                it()
            }
        }
        Column(modifier = Modifier.weight(1f)) { content() }
    }
}