package fi.tuska.beerclock.screens.statistics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.drinks.CategoryStatistics
import fi.tuska.beerclock.drinks.DrinkTimeService
import fi.tuska.beerclock.images.smallImage
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.ui.components.AppListItem
import fi.tuska.beerclock.ui.components.Gauge
import fi.tuska.beerclock.ui.components.UnitAvatar
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.minus

@Composable
fun CategoryStatisticsView(stats: StatisticsData) {
    val strings = Strings.get()
    val times = DrinkTimeService()
    Column(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp))
    ) {
        val period = stats.period
        val byCategory = stats.categoryStatistics
        AppListItem(
            headline = strings.statistics.periodTitle(period),
            supporting = "${strings.date(times.currentDrinkDay(period.range.start))} - ${
                strings.date(
                    times.currentDrinkDay(period.range.end) - DatePeriod(days = 1)
                )
            }",
            trailingContent = {
                Gauge(
                    byCategory.weeklyGaugeValue,
                    modifier = Modifier.size(64.dp)
                )
            })
        StatisticsGraph(stats)
        CategoryRow(byCategory.totalStats)
        byCategory.list.map { CategoryRow(it, compact = true) }
    }
}

@Composable
private fun CategoryRow(stats: CategoryStatistics, compact: Boolean = false) {
    val strings = Strings.get()
    val drinks = strings.drink.totalDrinkCount(stats.drinkCount)
    val quantity = strings.drink.totalQuantity(stats.totalQuantityLiters)
    AppListItem(
        icon = {
            Row(
                modifier = Modifier.width(64.dp),
                horizontalArrangement = Arrangement.Center
            ) { stats.categoryImage.smallImage(size = if (compact) 56.dp else 64.dp) }
        },
        overline = stats.title,
        headline = if (compact) "$drinks ($quantity)" else drinks,
        supporting = if (compact) null else quantity,
        trailingContent = { UnitAvatar(units = stats.totalUnits) },
        tonalElevation = if (compact) 4.dp else 24.dp
    )
}
