package fi.tuska.beerclock.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import fi.tuska.beerclock.ui.SubLayout

object DrinksScreen : Screen {

    @Composable
    override fun Content() {
        SubLayout(
            content = { Text("Juomien asetukset. Täältä voi muokata juomalistaa.") },
            title = "Juomat"
        )
    }
}
