package fi.tuska.beerclock.screens.newdrink

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import fi.tuska.beerclock.database.DrinkRecord
import fi.tuska.beerclock.database.LocalDatabase
import fi.tuska.beerclock.drinks.DrinkService
import fi.tuska.beerclock.localization.strings
import fi.tuska.beerclock.logging.getLogger
import fi.tuska.beerclock.ui.components.DateInputField
import fi.tuska.beerclock.ui.components.DecimalField
import fi.tuska.beerclock.ui.components.TimeInputField
import fi.tuska.beerclock.ui.composables.ViewModel
import fi.tuska.beerclock.ui.composables.rememberWithDispose
import fi.tuska.beerclock.ui.layout.SubLayout
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
fun TimePickerState.Companion.fromLocalTime(localTime: LocalDateTime): TimePickerState {
    return TimePickerState(
        initialHour = localTime.hour,
        initialMinute = localTime.minute,
        is24Hour = true
    )
}


object NewDrinkScreen : Screen {

    val gap = 16.dp

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val coroutineScope = rememberCoroutineScope()
        val db = LocalDatabase.current
        val vm = rememberWithDispose { NewDrinkScreenViewModel(DrinkService(db), navigator) }

        SubLayout(title = strings.newDrink.title, content = { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding).padding(16.dp).fillMaxSize()) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    DateInputField(
                        value = vm.date,
                        onValueChange = { vm.date = it },
                        labelText = strings.newDrink.dateLabel,
                        modifier = Modifier.weight(1f),
                    )
                    Spacer(modifier = Modifier.width(gap))
                    TimeInputField(
                        value = vm.time,
                        onValueChange = { vm.time = it },
                        labelText = strings.newDrink.timeLabel,
                        modifier = Modifier.weight(1f),
                    )
                }
                Spacer(modifier = Modifier.height(gap))
                TextField(
                    label = { Text(strings.newDrink.nameLabel) },
                    value = vm.name,
                    onValueChange = { vm.name = it },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(gap))
                Row(modifier = Modifier.fillMaxWidth()) {
                    DecimalField(
                        label = { Text(strings.newDrink.abvLabel) },
                        value = vm.abv,
                        onValueChange = { vm.abv = it },
                        modifier = Modifier.weight(1f),
                        trailingIcon = { Text(strings.newDrink.abvUnit) }
                    )
                    Spacer(modifier = Modifier.width(gap))
                    DecimalField(
                        label = { Text(strings.newDrink.quantityLabel) },
                        value = vm.quantityCl,
                        onValueChange = { vm.quantityCl = it },
                        modifier = Modifier.weight(1f),
                        trailingIcon = { Text(strings.newDrink.quantityUnit) }
                    )
                }
                Slider(
                    value = vm.quantityCl.toFloat(),
                    onValueChange = { vm.quantityCl = it.toInt().toDouble() },
                    valueRange = 1f..75f,
                    steps = 74
                )
                Spacer(modifier = Modifier.weight(1f))
                Row(modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally)) {
                    Button(onClick = { vm.addDrink() }) { Text(strings.newDrink.submit) }
                }
            }
        })
    }
}

private val logger = getLogger("NewDrinkScreen")

class NewDrinkScreenViewModel(
    private val drinkService: DrinkService,
    private val navigator: Navigator
) : ViewModel() {
    val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val drinks = mutableStateListOf<DrinkRecord>()
    var name = ""
    var abv = 4.5
    var quantityCl = 33.0
    var time = today.time
    var date = today.date

    fun addDrink() {
        launch {
            logger.info("Adding a new drink to database")
            drinkService.insertDrink()
            navigator.pop()
        }
    }
}
