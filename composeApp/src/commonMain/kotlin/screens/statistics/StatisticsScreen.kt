package fi.tuska.beerclock.screens.statistics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.ui.composables.rememberWithDispose
import fi.tuska.beerclock.ui.layout.MainLayout

class StatisticsScreen(private val period: StatisticsPeriod? = null) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val vm = rememberWithDispose(period) { StatisticsViewModel(period, navigator) }
        val scrollState = rememberScrollState()
        MainLayout(showTopBar = false) { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding).padding(16.dp).verticalScroll(scrollState)
            ) {
                StatisticsPage(vm)
            }
        }
    }
}

@Composable
fun StatisticsPage(vm: StatisticsViewModel) {
    PeriodSelector(vm)
    Spacer(modifier = Modifier.height(8.dp))
    StatisticsTitle(vm.period)
    Spacer(modifier = Modifier.height(8.dp))
    vm.statistics.valueOrNull()?.let { stats ->
        CategoryStatisticsView(stats)
    }
}

@Composable
fun StatisticsTitle(period: StatisticsPeriod) {
    val strings = Strings.get()
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Text(
            strings.statistics.periodTitle(period),
            style = MaterialTheme.typography.titleLarge
        )
    }
}
