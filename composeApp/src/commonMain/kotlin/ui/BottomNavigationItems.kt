package fi.tuska.beerclock.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector
import cafe.adriel.voyager.core.screen.Screen
import fi.tuska.beerclock.screens.DrinksScreen
import fi.tuska.beerclock.screens.HomeScreen

data class BottomNavigationItem(
    val label: String,
    val icon: ImageVector,
    val screen: Screen
)

fun bottomNavigationItems(): List<BottomNavigationItem> {
    return listOf(
        BottomNavigationItem(
            label = "Home",
            icon = Icons.Filled.Home,
            screen = HomeScreen
        ),
        BottomNavigationItem(
            label = "Drinks",
            icon = Icons.Filled.List,
            screen = DrinksScreen
        ),
    )
}