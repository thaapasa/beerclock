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
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import fi.tuska.beerclock.drinks.drinkAndThen
import fi.tuska.beerclock.screens.drinks.create.NewDrinkButton
import fi.tuska.beerclock.ui.components.BacStatusCard
import fi.tuska.beerclock.ui.composables.rememberWithDispose
import fi.tuska.beerclock.ui.layout.MainLayout
import fi.tuska.beerclock.util.SuspendAction

class HomeScreen(private val initAction: SuspendAction<HomeViewModel>? = null) : Screen {

    @Composable
    override fun Content() {
        val vm = rememberWithDispose { HomeViewModel(initAction) }
        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(Unit) {
            vm.loadTodaysDrinks()
        }

        MainLayout(
            actionButton = {
                NewDrinkButton(drinkAndThen { drink ->
                    navigator.replaceAll(HomeScreen { it.showDrinkAdded(drink) })
                })
            },
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
