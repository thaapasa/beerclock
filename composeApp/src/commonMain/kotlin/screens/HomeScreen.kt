package fi.tuska.beerclock.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
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
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

val drinks = listOf("Beer", "Wine", "Tequila", "Whisky", "Cognac", "Gin Tonic")

fun todaysDate(): String {
    fun LocalDateTime.formatted() = "$dayOfMonth.$monthNumber.$year $hour:$minute"

    val now = Clock.System.now()
    val zone = TimeZone.currentSystemDefault()
    return now.toLocalDateTime(zone).formatted()
}

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
        MainLayout(content = { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                DropdownMenuItem(onClick = {}, text = {
                    Text(todaysDate())
                })
                LazyColumn {
                    items(drinksList) {
                        DropdownMenuItem(onClick = {
                            coroutineScope.launch(Dispatchers.IO) {
                                db.drinksQueries.delete(it.id)
                                val drinks = db.drinksQueries.selectAll().executeAsList()
                                drinksList.clear()
                                drinksList.addAll(drinks)
                            }
                        }, text = {
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
