package fi.tuska.beerclock.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun BottomNavigationBar(onNavigate: (screen: Screen) -> Unit) {
    return NavigationBar {
        bottomNavigationItems().forEachIndexed { index, navigationItem ->
            NavigationBarItem(
                selected = false,
                label = { Text(navigationItem.label) },
                icon = {
                    Icon(
                        navigationItem.icon,
                        contentDescription = navigationItem.label
                    )
                },
                onClick = {
                    onNavigate(navigationItem.screen)
                }
            )
        }
    }
}