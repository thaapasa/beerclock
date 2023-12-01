package fi.tuska.beerclock.screens.today

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.images.AppImage
import fi.tuska.beerclock.localization.strings
import fi.tuska.beerclock.screens.history.UnitAvatar
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


@Composable
fun BacStatusCard(vm: HomeViewModel) {
    return Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row {
            DateView("You are sober", modifier = Modifier.weight(1f).padding(16.dp))
            Row(modifier = Modifier.padding(16.dp)) {
                UnitAvatar(
                    vm.units(),
                    modifier = Modifier.align(Alignment.CenterVertically).padding(end = 8.dp)
                )
                Image(
                    painter = AppImage.GAUGE.painter(),
                    contentDescription = "Tilanne",
                    modifier = Modifier.width(80.dp)
                )
            }
        }
    }
}

@Composable
fun DateView(statusText: String, modifier: Modifier = Modifier) {
    val now = Clock.System.now()
    val zone = TimeZone.currentSystemDefault()
    val time = now.toLocalDateTime(zone)
    return Column(modifier = modifier) {
        Text(
            strings.weekday(time.dayOfWeek).replaceFirstChar { it.titlecase() },
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            strings.dateShort(time),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(8.dp))
        Text(
            statusText, style = MaterialTheme.typography.bodyMedium,
            fontStyle = FontStyle.Italic
        )
    }
}
