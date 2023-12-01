package fi.tuska.beerclock.screens.newdrink

import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import fi.tuska.beerclock.drinks.DrinkTimeService
import fi.tuska.beerclock.util.MinutesInDay
import kotlinx.datetime.LocalTime

val MinuteSteps = MinutesInDay - 1
val MinuteRange = 0f..(MinutesInDay - 1).toFloat()

@Composable
fun DrinkTimeSlider(
    value: LocalTime,
    onValueChange: (time: LocalTime) -> Unit,
    modifier: Modifier = Modifier
) {
    val times = remember { DrinkTimeService() }
    var sliderVal by remember { mutableStateOf(times.toMinutesOfDay(value).toFloat()) }
    LaunchedEffect(value) {
        sliderVal = times.toMinutesOfDay(value).toFloat()
    }
    Slider(
        value = sliderVal,
        onValueChange = {
            sliderVal = it
            onValueChange(times.timeFromMinuteOfDay(it.toInt()))
        },
        modifier = modifier,
        valueRange = MinuteRange,
        steps = MinuteSteps
    )
}