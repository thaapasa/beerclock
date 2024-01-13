package fi.tuska.beerclock

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.Navigator
import fi.tuska.beerclock.database.DatabaseInfo
import fi.tuska.beerclock.graphs.GraphTheme
import fi.tuska.beerclock.logging.getLogger
import fi.tuska.beerclock.screens.today.HomeScreen
import fi.tuska.beerclock.ui.theme.AppTheme
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

val logger = getLogger("App")

@Composable
fun App() {
    remember { AppData() }
    AppTheme {
        GraphTheme {
            Navigator(
                screen = HomeScreen
            )
        }
    }
}

class AppData : KoinComponent {
    val dbInfo: DatabaseInfo = get()

    init {
        logger.info("BeerClock starting with SQLite ${dbInfo.sqliteVersion}, DB version ${dbInfo.databaseVersion}")
    }
}
