package fi.tuska.beerclock.common.screens

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.screen.Screen
import dev.icerock.moko.resources.compose.painterResource
import fi.tuska.beerclock.common.MR
import fi.tuska.beerclock.common.database.createDatabase
import fi.tuska.beerclock.common.ui.MainLayout

object HomeScreen : Screen {

    @Composable
    override fun Content() {

        remember {
            val database = createDatabase();
            val drinks = database.drinksQueries
            drinks.insert(100, "Juomani juu")
            println(drinks.selectAll().executeAsList())
        }

        MainLayout(content = {
            Text("Appiksen päänäkymä. Tähän laitetaan tieto tämän päivän tilanteesta")
        }, actionButton = {
            FloatingActionButton({}) {
                Icon(
                    painter = painterResource(MR.images.sports_bar),
                    contentDescription = "Juo!"
                )
            }
        }
        )
    }
}
