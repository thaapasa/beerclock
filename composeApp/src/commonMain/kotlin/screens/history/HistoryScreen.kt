package fi.tuska.beerclock.screens.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.localization.strings
import fi.tuska.beerclock.ui.components.DatePickerIcon
import fi.tuska.beerclock.ui.components.SegmentDivider
import fi.tuska.beerclock.ui.components.SegmentedButton
import fi.tuska.beerclock.ui.composables.rememberWithDispose
import fi.tuska.beerclock.ui.layout.MainLayout


object HistoryScreen : Screen {

    @Composable
    override fun Content() {
        val vm = rememberWithDispose { HistoryViewModel() }

        LaunchedEffect(Unit) {
            vm.loadDrinks()
        }

        return MainLayout(showTopBar = false) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding).padding(16.dp)) {
                SegmentedButton {
                    AppIcon.CHEVRON_LEFT.iconButton(
                        contentDescription = strings.history.prevDay,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    ) { vm.prevDay() }
                    SegmentDivider()
                    Text(
                        strings.date(vm.date),
                        modifier = Modifier.weight(1f)
                            .align(Alignment.CenterVertically),
                        textAlign = TextAlign.Center
                    )
                    SegmentDivider()
                    DatePickerIcon(
                        value = vm.date,
                        onValueChange = { vm.selectDay(it) },
                        modifier = Modifier.padding(horizontal = 4.dp),
                        title = strings.history.selectDay
                    )
                    SegmentDivider()
                    AppIcon.CHEVRON_RIGHT.iconButton(
                        contentDescription = strings.history.nextDay,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    ) { vm.nextDay() }
                }
                DrinkList(
                    vm.drinks,
                    modifier = Modifier.padding(top = 16.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    onDelete = vm::deleteDrink
                )
            }
        }
    }
}
