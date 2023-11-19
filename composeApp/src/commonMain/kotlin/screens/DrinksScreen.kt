package fi.tuska.beerclock.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import fi.tuska.beerclock.ui.SubLayout

object DrinksScreen : Screen {

    @Composable
    override fun Content() {
        SubLayout(
            content = { innerPadding ->
                Column(modifier = Modifier.padding(innerPadding)) {
                    Text("Juomien asetukset. Täältä voi muokata juomalistaa.")
                }
            },
            title = "Juomat"
        )
    }
}
