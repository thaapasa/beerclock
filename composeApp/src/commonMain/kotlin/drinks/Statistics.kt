package fi.tuska.beerclock.drinks

import fi.tuska.beerclock.database.GetStatisticsByCategory
import fi.tuska.beerclock.images.AppIcon
import fi.tuska.beerclock.images.DrinkImage
import fi.tuska.beerclock.localization.Strings
import fi.tuska.beerclock.screens.statistics.StatisticsPeriod
import fi.tuska.beerclock.settings.UserPreferences
import fi.tuska.beerclock.ui.components.GaugeValue

data class CategoryStatistics(
    val title: String,
    val categoryImage: DrinkImage,
    val totalUnits: Double,
    val totalQuantityLiters: Double,
    val drinkCount: Long,
    val order: Int
) {
    companion object {
        fun fromDb(row: GetStatisticsByCategory): CategoryStatistics {
            val category = row.category?.let(Category::forName)
            return CategoryStatistics(
                title = Strings.get().drink.categoryName(category),
                categoryImage = category?.image ?: DrinkImage.CAT_UNCATEGORIZED,
                totalUnits = row.totalUnits ?: 0.0,
                totalQuantityLiters = row.totalQuantityLiters ?: 0.0,
                drinkCount = row.drinkCount,
                order = category?.order ?: Int.MAX_VALUE
            )
        }
    }
}

fun List<CategoryStatistics>.calculateTotals(): CategoryStatistics {
    return CategoryStatistics(
        title = Strings.get().statistics.totalsTitle,
        categoryImage = DrinkImage.CAT_ALL,
        totalUnits = sumOf { it.totalUnits },
        totalQuantityLiters = sumOf { it.totalQuantityLiters },
        drinkCount = sumOf { it.drinkCount },
        order = 0
    )
}

class StatisticsByCategory(
    byCategories: List<CategoryStatistics>,
    val period: StatisticsPeriod,
    prefs: UserPreferences
) {
    val times = DrinkTimeService()
    val range = period.range
    val totalStats = byCategories.calculateTotals()
    val list = byCategories.sortedBy { it.order }
    val daysGone = range.lengthInDays(historicalDates = true)

    val weeksGone = daysGone.toDouble() / 7.0
    val weeklyUnits = if (weeksGone > 0.0) totalStats.totalUnits / weeksGone else 0.0
    val weeklyGaugeValue = GaugeValue(
        initialValue = weeklyUnits,
        maxValue = prefs.maxWeeklyUnits,
        appIcon = AppIcon.CALENDAR_WEEK
    )
}
