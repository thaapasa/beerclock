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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import fi.tuska.beerclock.database.LocalDatabase
import fi.tuska.beerclock.localization.strings
import fi.tuska.beerclock.ui.components.DateInputField
import fi.tuska.beerclock.ui.components.DecimalField
import fi.tuska.beerclock.ui.components.TimeInputField
import fi.tuska.beerclock.ui.layout.SubLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
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
        val now = Clock.System.now()
        val localTime = now.toLocalDateTime(TimeZone.currentSystemDefault())

        var name by remember { mutableStateOf("") }
        var abv by remember { mutableStateOf(4.5) }
        var quantityCl by remember { mutableStateOf(33.0) }
        var time by remember { mutableStateOf(localTime.time) }
        var date by remember { mutableStateOf(localTime.date) }

        SubLayout(title = strings.newDrink.title, content = { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding).padding(16.dp).fillMaxSize()) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    DateInputField(
                        value = date,
                        onValueChange = { date = it },
                        labelText = strings.newDrink.dateLabel,
                        modifier = Modifier.weight(1f),
                    )
                    Spacer(modifier = Modifier.width(gap))
                    TimeInputField(
                        value = time,
                        onValueChange = { time = it },
                        labelText = strings.newDrink.timeLabel,
                        modifier = Modifier.weight(1f),
                    )
                }
                Spacer(modifier = Modifier.height(gap))
                TextField(
                    label = { Text(strings.newDrink.nameLabel) },
                    value = name,
                    onValueChange = { name = it },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(gap))
                Row(modifier = Modifier.fillMaxWidth()) {
                    DecimalField(
                        label = { Text(strings.newDrink.abvLabel) },
                        value = abv,
                        onValueChange = { abv = it },
                        modifier = Modifier.weight(1f),
                        trailingIcon = { Text(strings.newDrink.abvUnit) }
                    )
                    Spacer(modifier = Modifier.width(gap))
                    DecimalField(
                        label = { Text(strings.newDrink.quantityLabel) },
                        value = quantityCl,
                        onValueChange = { quantityCl = it },
                        modifier = Modifier.weight(1f),
                        trailingIcon = { Text(strings.newDrink.quantityUnit) }
                    )
                }
                Slider(
                    value = quantityCl.toFloat(),
                    onValueChange = { quantityCl = it.toInt().toDouble() },
                    valueRange = 1f..75f,
                    steps = 74
                )
                Spacer(modifier = Modifier.weight(1f))
                Row(modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally)) {
                    Button(onClick = {
                        coroutineScope.launch(Dispatchers.IO) {
                            addNewDrink(db)
                            navigator.pop()
                        }
                    }) { Text(strings.newDrink.submit) }
                }
            }
        })
    }
}