package fi.tuska.beerclock.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import fi.tuska.beerclock.ui.SubLayout

object StatisticsScreen : Screen {

    @Composable
    override fun Content() {
        SubLayout(content = { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                Text("Tilastot")
            }
        }, title = "Tilastot")
    }
}
