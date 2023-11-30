package fi.tuska.beerclock.screens.history

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import fi.tuska.beerclock.database.LocalDatabase
import fi.tuska.beerclock.drinks.DrinkService
import fi.tuska.beerclock.ui.composables.rememberWithDispose
import fi.tuska.beerclock.ui.layout.MainLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

object HistoryScreen : Screen {

    @Composable
    override fun Content() {
        val db = LocalDatabase.current
        val vm = rememberWithDispose { HistoryViewModel(DrinkService(db)) }

        LaunchedEffect(Unit) {
            withContext(Dispatchers.IO) {
                vm.loadTodaysDrinks()
            }
        }

        return MainLayout(
            content = { innerPadding ->
                DrinkList(vm.drinks,
                    modifier = Modifier.padding(innerPadding),
                    onClick = { vm.deleteDrink(it) }
                )
            },
        )
    }
}
