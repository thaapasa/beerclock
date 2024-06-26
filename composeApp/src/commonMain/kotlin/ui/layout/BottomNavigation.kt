package fi.tuska.beerclock.ui.layout

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.screens.history.HistoryScreen
import fi.tuska.beerclock.screens.statistics.StatisticsScreen
import fi.tuska.beerclock.screens.today.HomeScreen

data class BottomNavigationItem(
    val label: String,
    val icon: AppIcon,
    val screen: Screen,
)

fun bottomNavigationItems(): List<BottomNavigationItem> {
    val strings = Strings.get()
    return listOf(
        BottomNavigationItem(
            label = strings.menu.today,
            icon = AppIcon.DRINK,
            screen = HomeScreen,
        ),
        BottomNavigationItem(
            label = strings.menu.history,
            icon = AppIcon.HISTORY,
            screen = HistoryScreen(),
        ),
        BottomNavigationItem(
            label = strings.menu.statistics,
            icon = AppIcon.GRAPH,
            screen = StatisticsScreen(),
        ),
    )
}

@Composable
fun BottomNavigationBar(current: Screen, onNavigate: (screen: Screen) -> Unit) {
    return NavigationBar {
        bottomNavigationItems().forEachIndexed { _, item ->
            NavigationBarItem(
                selected = item.screen.key == current.key,
                label = { Text(item.label) },
                icon = {
                    Icon(
                        painter = item.icon.painter(),
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
