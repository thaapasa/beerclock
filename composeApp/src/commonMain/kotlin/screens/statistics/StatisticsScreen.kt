package fi.tuska.beerclock.screens.statistics

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.ui.components.SegmentDivider
import fi.tuska.beerclock.ui.components.SegmentedButton
import fi.tuska.beerclock.ui.composables.rememberWithDispose
import fi.tuska.beerclock.ui.layout.MainLayout

class StatisticsScreen(private val period: StatisticsPeriod? = null) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val vm = rememberWithDispose(period) { StatisticsViewModel(period, navigator) }
        val strings = Strings.get()
        MainLayout(showTopBar = false) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding).padding(16.dp)) {
                SegmentedButton {
                    AppIcon.CHEVRON_LEFT.iconButton(
                        contentDescription = strings.history.prevDay,
                        modifier = Modifier.padding(start = 8.dp, end = 4.dp)
                    ) { vm.prev() }
                    SegmentDivider()
                    YearTitle(
                        vm.asYear(),
                        selected = vm.period.period == StatisticsPeriodType.YEAR,
                        modifier = Modifier.weight(1f),
                        onSelect = vm::show
                    )
                    SegmentDivider()
                    MonthTitle(
                        vm.asMonth(),
                        selected = vm.period.period == StatisticsPeriodType.MONTH,
                        modifier = Modifier.weight(1.33f),
                        onSelect = vm::show
                    )
                    SegmentDivider()
                    WeekTitle(
                        vm.asWeek(),
                        selected = vm.period.period == StatisticsPeriodType.WEEK,
                        modifier = Modifier.weight(1f),
                        onSelect = vm::show
                    )
                    SegmentDivider()
                    AppIcon.CHEVRON_RIGHT.iconButton(
                        contentDescription = strings.history.nextDay,
                        modifier = Modifier.padding(start = 4.dp, end = 8.dp)
                    ) { vm.next() }
                }
            }
        }
    }
}
