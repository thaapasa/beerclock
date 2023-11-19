package fi.tuska.beerclock.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import fi.tuska.beerclock.database.LocalDatabase
import fi.tuska.beerclock.localization.strings
import fi.tuska.beerclock.ui.MainLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

object AddDrinkScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val coroutineScope = rememberCoroutineScope()

        val db = LocalDatabase.current

        MainLayout(content = {
            Column(modifier = Modifier.fillMaxSize()) {
                Row {
                    Text(strings.newDrink.title)
                }
                Spacer(modifier = Modifier.weight(1f))
                Row(modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally)) {
                    Button(onClick = {
                        coroutineScope.launch(Dispatchers.IO) {
                            db.drinksQueries.insert(1321, drinks.random())
                            navigator.pop()
                        }
                    }) { Text(strings.newDrink.submit) }
                }
            }
        })
    }
}
