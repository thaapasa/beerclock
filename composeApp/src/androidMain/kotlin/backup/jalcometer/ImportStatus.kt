package fi.tuska.beerclock.backup.jalcometer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class ImportStatus(val message: String, val percentage: Float? = null)

@Composable
fun StatusDisplay(status: ImportStatus, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        Text(
            status.message,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
        )
        status.percentage?.let {
            LinearProgressIndicator(
                progress = { it },
                modifier = Modifier.fillMaxWidth()
            )
        } ?: LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
    }
}
