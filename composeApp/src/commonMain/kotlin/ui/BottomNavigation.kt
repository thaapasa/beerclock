package fi.tuska.beerclock.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import fi.tuska.beerclock.screens.HistoryScreen
import fi.tuska.beerclock.screens.HomeScreen
import fi.tuska.beerclock.screens.StatisticsScreen
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

data class BottomNavigationItem(
    val label: String,
    val icon: String,
    val screen: Screen
)

fun bottomNavigationItems(): List<BottomNavigationItem> {
    return listOf(
        BottomNavigationItem(
            label = "Home",
            icon = "drawable/local_bar.xml",
            screen = HomeScreen
        ),
        BottomNavigationItem(
            label = "Drinks",
            icon = "drawable/history.xml",
            screen = HistoryScreen
        ),
        BottomNavigationItem(
            label = "Statistics",
            icon = "drawable/graph.xml",
            screen = StatisticsScreen
        ),
    )
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun BottomNavigationBar(current: Screen, onNavigate: (screen: Screen) -> Unit) {
    return NavigationBar {
        bottomNavigationItems().forEachIndexed { index, item ->
            NavigationBarItem(
                selected = item.screen == current,
                label = { Text(item.label) },
                icon = {
                    Icon(
                        painter = painterResource(item.icon),
                        contentDescription = item.label
                    )
                },
                onClick = {
                    onNavigate(item.screen)
                }
            )
        }
    }
}