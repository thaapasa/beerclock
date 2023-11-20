package fi.tuska.beerclock.components

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
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun DrinksListItem(drink: Drinks, onClick: () -> Unit) {
    ListItem(headlineContent = {
        Text("Drink: ${drink.drink}")
    }, leadingContent = {
        Image(
            painter = painterResource(imgForDrink(drink)),
            contentDescription = "Beer",
            modifier = Modifier.width(64.dp).clip(RoundedCornerShape(16.dp)),
        )
    }, modifier = Modifier.clickable { onClick() }, tonalElevation = 8.dp, shadowElevation = 16.dp)
}

val drinks = listOf("drink_beer.webp", "drink_beer2.webp", "drink_whisky.webp", "drink_can.webp")
fun imgForDrink(drink: Drinks): String {
    return "drawable/${drinks[(drink.id % drinks.size).toInt()]}"
}
