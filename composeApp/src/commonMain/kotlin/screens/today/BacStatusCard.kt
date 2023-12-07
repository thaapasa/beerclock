package fi.tuska.beerclock.screens.today

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.ui.components.Gauge
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


@Composable
fun BacStatusCard(vm: HomeViewModel) {
    val strings = Strings.get()
    return Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row {
            DateView("You are sober", modifier = Modifier.weight(1f).padding(16.dp))
            Row(modifier = Modifier.padding(16.dp)) {
                Gauge(
                    value = strings.drink.units(vm.bac()),
                    position = vm.bacPosition(),
                    modifier = Modifier.size(64.dp),
                    iconPainter = AppIcon.BOLT.painter(),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Gauge(
                    value = strings.drink.units(vm.units()),
                    position = vm.unitsPosition(),
                    modifier = Modifier.size(64.dp),
                    iconPainter = AppIcon.DRINK.painter(),
                )
            }
        }
    }
}

@Composable
fun DateView(statusText: String, modifier: Modifier = Modifier) {
    val strings = Strings.get()
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
