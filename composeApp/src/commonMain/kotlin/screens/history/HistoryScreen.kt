package fi.tuska.beerclock.screens.history

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import fi.tuska.beerclock.database.Drinks
import fi.tuska.beerclock.database.LocalDatabase
import fi.tuska.beerclock.ui.layout.MainLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

object HistoryScreen : Screen {

    @Composable
    override fun Content() {

        val db = LocalDatabase.current
        val drinksList = remember { mutableStateListOf<Drinks>() }

        LaunchedEffect(Unit) {
            withContext(Dispatchers.IO) {
                val drinks = db.drinksQueries.selectAll().executeAsList()
                drinksList.addAll(drinks)
            }
        }

        return MainLayout(
            content = { innerPadding ->
                DrinksList(drinksList, modifier = Modifier.padding(innerPadding))
            },
        )
    }
}