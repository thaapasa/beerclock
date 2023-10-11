package fi.tuska.beerclock.common.screens

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
import dev.icerock.moko.resources.compose.painterResource
import fi.tuska.beerclock.common.MR
import fi.tuska.beerclock.common.database.Drinks
import fi.tuska.beerclock.common.database.createDatabase
import fi.tuska.beerclock.common.ui.MainLayout
import kotlinx.coroutines.launch

val drinks = listOf("Beer", "Wine", "Tequila", "Whisky", "Cognac", "Gin Tonic")

object HomeScreen : Screen {

    @Composable
    override fun Content() {
        val coroutineScope = rememberCoroutineScope()
        val db = remember { createDatabase() }
        val drinksList = remember { mutableStateListOf<Drinks>() }

        LaunchedEffect(Unit) {
            val drinks = db.drinksQueries.selectAll().executeAsList()
            drinksList.addAll(drinks)
        }
        MainLayout(content = {
            Column {
                LazyColumn {
                    items(drinksList) {
                        DropdownMenuItem(onClick = {
                            coroutineScope.launch {
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
                coroutineScope.launch {
                    db.drinksQueries.insert(1321, drinks.random())
                    val drinks = db.drinksQueries.selectAll().executeAsList()
                    drinksList.clear()
                    drinksList.addAll(drinks)
                }
            }) {
                Icon(
                    painter = painterResource(MR.images.sports_bar),
                    contentDescription = "Juo!"
                )
            }
        }
        )
    }
}
