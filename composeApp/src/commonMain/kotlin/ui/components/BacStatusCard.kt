package fi.tuska.beerclock.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.localization.Strings
import kotlinx.datetime.LocalDate


interface BacStatusViewModel {
    val gauges: List<GaugeValueWithHelp>

    @Composable
    fun Content()
}


@Composable
fun BacStatusCard(vm: BacStatusViewModel, modifier: Modifier = Modifier) {
    StatusCard(modifier = modifier) {
        Row {
            vm.Content()
            Spacer(modifier = Modifier.weight(1f))
            Row(modifier = Modifier.padding(8.dp)) {
                vm.gauges.mapIndexed { ind, gauge ->
                    if (ind > 0) {
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    HelpButton(text = gauge.helpText) { onClick ->
                        Gauge(
                            value = gauge,
                            modifier = Modifier.size(64.dp).clip(
                                RoundedCornerShape(32.dp)
                            ).clickable(onClick = onClick),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DateView(drinkDay: LocalDate, modifier: Modifier = Modifier) {
    val strings = Strings.get()
    return Column(modifier = modifier) {
        Text(
            strings.weekday(drinkDay.dayOfWeek).replaceFirstChar { it.titlecase() },
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            strings.dateShort(drinkDay),
            style = MaterialTheme.typography.titleLarge
        )
    }
}
