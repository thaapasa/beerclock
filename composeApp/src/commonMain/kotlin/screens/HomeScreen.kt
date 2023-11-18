package fi.tuska.beerclock.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import fi.tuska.beerclock.database.Drinks
import fi.tuska.beerclock.database.LocalDatabase
import fi.tuska.beerclock.ui.MainLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

val drinks = listOf("Beer", "Wine", "Tequila", "Whisky", "Cognac", "Gin Tonic")

@OptIn(ExperimentalResourceApi::class)
object HomeScreen : Screen {

    @Composable
    override fun Content() {
        val coroutineScope = rememberCoroutineScope()
        val db = LocalDatabase.current
        val drinksList = remember { mutableStateListOf<Drinks>() }
        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(Unit) {
            withContext(Dispatchers.IO) {
                val drinks = db.drinksQueries.selectAll().executeAsList()
                drinksList.addAll(drinks)
            }
        }
        MainLayout(content = {
            Column {
                LazyColumn {
                    items(drinksList) {
                        DropdownMenuItem(onClick = {
                            coroutineScope.launch(Dispatchers.IO) {
                                db.drinksQueries.delete(it.id)
                                val drinks = db.drinksQueries.selectAll().executeAsList()
                                drinksList.clear()
                                drinksList.addAll(drinks)
                            }
                        }, content = {
                            Text("Drink: ${it.drink}")
                        })
                    }
                }
            }
        }, actionButton = {
            FloatingActionButton(onClick = {
                navigator.push(AddDrinkScreen)
            }) {
                Icon(
                    painter = painterResource("drawable/sports_bar.xml"),
                    contentDescription = "Juo!"
                )
            }
        }
        )
    }
}
