package fi.tuska.beerclock.screens.today

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import fi.tuska.beerclock.database.LocalDatabase
import fi.tuska.beerclock.drinks.DrinkRecordInfo
import fi.tuska.beerclock.drinks.DrinkService
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.localization.strings
import fi.tuska.beerclock.screens.history.DrinkList
import fi.tuska.beerclock.screens.newdrink.NewDrinkScreen
import fi.tuska.beerclock.ui.composables.ViewModel
import fi.tuska.beerclock.ui.composables.rememberWithDispose
import fi.tuska.beerclock.ui.layout.MainLayout
import kotlinx.coroutines.launch


object HomeScreen : Screen {

    @Composable
    override fun Content() {
        val db = LocalDatabase.current
        val vm = rememberWithDispose { HomeScreenViewModel(DrinkService(db)) }
        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(Unit) {
            vm.loadTodaysDrinks()
        }
        MainLayout(content = { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding).padding(16.dp)) {
                BacStatusCard()
                Spacer(modifier = Modifier.height(16.dp))
                DrinkList(
                    vm.drinks,
                    modifier = Modifier.clip(RoundedCornerShape(12.dp)),
                    onClick = { vm.deleteDrink(it) })
            }
        }, actionButton = {
            LargeFloatingActionButton(onClick = {
                navigator.push(NewDrinkScreen)
            }) {
                Icon(
                    painter = AppIcon.DRINK.painter(),
                    contentDescription = strings.newDrink.title,
                    modifier = Modifier.size(36.dp)
                )
            }
        }
        )
    }
}

class HomeScreenViewModel(private val drinkService: DrinkService) : ViewModel() {
    val drinks = mutableStateListOf<DrinkRecordInfo>()

    fun loadTodaysDrinks() {
        launch {
            drinks.clear()
            val newDrinks = drinkService.getDrinksForToday()
            drinks.addAll(newDrinks)
        }
    }

    fun deleteDrink(drink: DrinkRecordInfo) {
        launch {
            drinkService.deleteDrinkById(drink.id)
            drinks.remove(drink)
        }
    }
}
