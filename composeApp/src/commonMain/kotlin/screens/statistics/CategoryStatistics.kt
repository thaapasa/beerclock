package fi.tuska.beerclock.screens.statistics

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import fi.tuska.beerclock.drinks.CategoryStatistics
import fi.tuska.beerclock.images.smallImage
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.ui.components.AppListItem
import fi.tuska.beerclock.ui.components.UnitAvatar

@Composable
fun CategoryStatisticsView(stats: List<CategoryStatistics>) {
    Column(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp))
    ) {
        stats.map { CategoryRow(it) }
    }
}

@Composable
private fun CategoryRow(stats: CategoryStatistics) {
    val strings = Strings.get()
    AppListItem(
        icon = { stats.categoryImage.smallImage() },
        overline = stats.title,
        headline = strings.drink.totalDrinkCount(stats.drinkCount),
        supporting = strings.drink.totalQuantity(stats.totalQuantityLiters),
        trailingContent = { UnitAvatar(units = stats.totalUnits) },
    )
}
