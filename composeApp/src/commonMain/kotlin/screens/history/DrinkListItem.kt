package fi.tuska.beerclock.screens.history

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.database.DrinkRecord
import fi.tuska.beerclock.database.fromDbTime
import fi.tuska.beerclock.images.DrinkImage
import fi.tuska.beerclock.localization.strings
import kotlinx.datetime.Instant

@Composable
fun DrinksListItem(drink: DrinkRecord, onClick: () -> Unit = { }) {
    ListItem(
        overlineContent = { Text(strings.drink.drinkTime(Instant.fromDbTime(drink.time))) },
        headlineContent = { Text(drink.name) },
        supportingContent = {
            Text(
                strings.drink.itemDescription(quantity = drink.quantity_liters, abv = drink.abv)
            )
        },
        leadingContent = {
            Image(
                painter = drink.image().painter(),
                contentDescription = "Beer",
                modifier = Modifier.width(64.dp).clip(RoundedCornerShape(12.dp)),
            )
        },
        trailingContent = { UnitsAvatar(units = 1.1) },
        modifier = Modifier.clickable { onClick() },
        tonalElevation = 8.dp,
        shadowElevation = 16.dp
    )
}


@Composable
fun UnitsAvatar(
    units: Double,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(60.dp)
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
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Text(
                    text = strings.drink.unitLabel,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Spacer(modifier = Modifier.height(5.dp))
            }
        }
    }
}

@Composable
fun DrinkRecord.image(): DrinkImage {
    return DrinkImage.forName(this.image)
}
