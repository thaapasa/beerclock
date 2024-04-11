package fi.tuska.beerclock.screens.today

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.screens.ParcelableScreen
import fi.tuska.beerclock.screens.drinks.create.NewDrinkButton
import fi.tuska.beerclock.ui.components.BacStatusCard
import fi.tuska.beerclock.ui.composables.rememberWithDispose
import fi.tuska.beerclock.ui.layout.MainLayout
import fi.tuska.beerclock.util.CommonParcelize

@CommonParcelize
object HomeScreen : ParcelableScreen {

    @Composable
    override fun Content() {
        val vm = rememberWithDispose { HomeViewModel() }

        LaunchedEffect(Unit) {
            vm.loadTodaysDrinks()
        }

        MainLayout(
            actionButton = { NewDrinkButton() },
            snackbarHostState = vm.snackbar,
        ) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding).padding(16.dp)) {
                BacStatusCard(vm)
                Spacer(modifier = Modifier.height(16.dp))
                DailyBacGraph(bac = vm.bacStatus, now = vm.now)
            }
        }
    }
}
