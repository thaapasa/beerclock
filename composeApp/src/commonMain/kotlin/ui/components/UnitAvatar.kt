package fi.tuska.beerclock.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.localization.Strings

@Composable
fun UnitAvatar(units: Double, modifier: Modifier = Modifier, size: Dp = 60.dp) {
    val strings = Strings.get()
    val valueTextSize = when {
        units > 100 -> MaterialTheme.typography.bodySmall
        units > 10 -> MaterialTheme.typography.bodyMedium
        else -> MaterialTheme.typography.bodyLarge
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(size)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(60.dp)
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = CircleShape
                )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = strings.drink.units(units),
                    style = valueTextSize,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Text(
                    text = strings.drink.unitLabel(units),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Spacer(modifier = Modifier.height(5.dp))
            }
        }
    }
}
