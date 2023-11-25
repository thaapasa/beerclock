package fi.tuska.beerclock.screens.history

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.database.Drinks
import fi.tuska.beerclock.images.DrinkImage

@Composable
fun DrinksListItem(drink: Drinks, onClick: () -> Unit = { }) {
    ListItem(headlineContent = {
        Text(drink.name)
    }, leadingContent = {
        Image(
            painter = drink.image().painter(),
            contentDescription = "Beer",
            modifier = Modifier.width(64.dp).clip(RoundedCornerShape(12.dp)),
        )
    }, modifier = Modifier.clickable { onClick() }, tonalElevation = 8.dp, shadowElevation = 16.dp)
}

@Composable
fun Drinks.image(): DrinkImage {
    return DrinkImage.forName(this.image)
}
