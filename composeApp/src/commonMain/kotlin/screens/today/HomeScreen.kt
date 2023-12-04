package fi.tuska.beerclock.screens.today

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import fi.tuska.beerclock.screens.newdrink.NewDrinkButton
import fi.tuska.beerclock.ui.composables.rememberWithDispose
import fi.tuska.beerclock.ui.layout.MainLayout


object HomeScreen : Screen {

    @Composable
    override fun Content() {
        val vm = rememberWithDispose { HomeViewModel() }

        LaunchedEffect(Unit) {
            vm.loadTodaysDrinks()
        }

        MainLayout(
            actionButton = { NewDrinkButton(onDrinksUpdated = vm::reload) }
        ) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding).padding(16.dp)) {
                BacStatusCard(vm)
                Spacer(modifier = Modifier.height(16.dp))
                DailyBacGraph(bac = vm.bac)
            }
        }
    }
}
