package fi.tuska.beerclock.screens.today

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
        val bacPosition = animateFloatAsState(targetValue = vm.bacPosition)
        val bac = animateFloatAsState(targetValue = vm.bac)
        val units = animateFloatAsState(targetValue = vm.units)
        val unitsPosition = animateFloatAsState(targetValue = vm.unitsPosition)
        Row {
            DateView(modifier = Modifier.weight(1f).padding(16.dp))
            Row(modifier = Modifier.padding(8.dp)) {
                Gauge(
                    value = strings.drink.units(bac.value.toDouble()),
                    position = bacPosition.value,
                    modifier = Modifier.size(64.dp),
                    icon = { Text(text = "‰", color = MaterialTheme.colorScheme.primary) },
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Gauge(
                    value = strings.drink.units(units.value.toDouble()),
                    position = unitsPosition.value,
                    modifier = Modifier.size(64.dp),
                    iconPainter = AppIcon.DRINK.painter(),
                )
            }
        }
    }
}

@Composable
fun DateView(modifier: Modifier = Modifier) {
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
            style = MaterialTheme.typography.titleLarge
        )
    }
}
