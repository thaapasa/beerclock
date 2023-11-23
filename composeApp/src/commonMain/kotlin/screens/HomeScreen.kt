package fi.tuska.beerclock.screens

import DrinksList
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import fi.tuska.beerclock.components.BacStatusCard
import fi.tuska.beerclock.database.Drinks
import fi.tuska.beerclock.database.LocalDatabase
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.ui.MainLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.ExperimentalResourceApi

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
        MainLayout(content = { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding).padding(16.dp)) {
                BacStatusCard()
                Spacer(modifier = Modifier.height(16.dp))
                DrinksList(
                    drinksList,
                    modifier = Modifier.clip(RoundedCornerShape(12.dp)),
                    onClick = {
                        coroutineScope.launch(Dispatchers.IO) {
                            db.drinksQueries.delete(it.id)
                            val drinks = db.drinksQueries.selectAll().executeAsList()
                            drinksList.clear()
                            drinksList.addAll(drinks)
                        }
                    })
            }
        }, actionButton = {
            LargeFloatingActionButton(onClick = {
                navigator.push(AddDrinkScreen)
            }) {
                Icon(
                    painter = AppIcon.DRINK.painter(),
                    contentDescription = "Juo!",
                    modifier = Modifier.size(36.dp)
                )
            }
        }
        )
    }
}
