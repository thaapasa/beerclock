package fi.tuska.beerclock.screens.statistics

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import fi.tuska.beerclock.localization.Strings


@Composable
fun TwoRowTitle(
    label: String,
    value: String,
    selected: Boolean = false,
    modifier: Modifier = Modifier
) {
    Column(
        modifier.fillMaxHeight().background(
            color = if (selected) MaterialTheme.colorScheme.primaryContainer
            else MaterialTheme.colorScheme.secondaryContainer
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            label,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            value,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun RowScope.YearTitle(
    period: StatisticsYear,
    selected: Boolean = false,
    modifier: Modifier = Modifier,
    onSelect: (period: StatisticsYear) -> Unit,
) {
    val strings = Strings.get()
    TwoRowTitle(
        strings.statistics.yearTitle,
        period.year.toString(),
        selected = selected,
        modifier = modifier.clickable { onSelect(period) }
    )
}

@Composable
fun RowScope.MonthTitle(
    period: StatisticsMonth,
    selected: Boolean = false,
    modifier: Modifier = Modifier,
    onSelect: (period: StatisticsMonth) -> Unit,
) {
    val strings = Strings.get()
    TwoRowTitle(
        strings.statistics.monthTitle,
        strings.statistics.monthValue(period.year, period.month),
        selected = selected,
        modifier = modifier.clickable { onSelect(period) }
    )
}

@Composable
fun RowScope.WeekTitle(
    period: StatisticsWeek,
    selected: Boolean = false,
    modifier: Modifier = Modifier,
    onSelect: (period: StatisticsWeek) -> Unit,
) {
    val strings = Strings.get()
    TwoRowTitle(
        strings.statistics.weekTitle,
        strings.statistics.weekValue(period.weekOfYear.year, period.weekOfYear.weekNumber),
        selected = selected,
        modifier = modifier.clickable { onSelect(period) }
    )
}
