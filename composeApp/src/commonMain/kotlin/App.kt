package fi.tuska.beerclock

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import fi.tuska.beerclock.database.DatabaseInfo
import fi.tuska.beerclock.graphs.GraphTheme
import fi.tuska.beerclock.logging.getLogger
import fi.tuska.beerclock.screens.disclosure.DisclosureScreen
import fi.tuska.beerclock.screens.today.HomeScreen
import fi.tuska.beerclock.settings.GlobalUserPreferences
import fi.tuska.beerclock.ui.composables.ViewModel
import fi.tuska.beerclock.ui.composables.rememberWithDispose
import fi.tuska.beerclock.ui.theme.AppTheme
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

private val logger = getLogger("App")

@Composable
fun App() {
    val vm = rememberWithDispose { AppDataViewModel() }
    AppTheme {
        GraphTheme {
            Navigator(
                screen = vm.getHomeScreen()
            )
        }
    }
}

class AppDataViewModel : ViewModel(), KoinComponent {
    private val dbInfo: DatabaseInfo = get()
    private val prefs: GlobalUserPreferences = get()

    private fun hasAgreedToDisclosure() = prefs.prefs.hasAgreedDisclosure

    init {
        logger.info("BeerClock starting with SQLite ${dbInfo.sqliteVersion}, DB version ${dbInfo.databaseVersion}")
        logger.info("User ${if (hasAgreedToDisclosure()) "has" else "has not"} agreed to disclosure")
    }
    
    fun getHomeScreen(): Screen = when {
        hasAgreedToDisclosure() -> HomeScreen()
        else -> DisclosureScreen
    }
}
