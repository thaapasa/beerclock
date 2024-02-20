package fi.tuska.beerclock.drinks

import fi.tuska.beerclock.database.GetStatisticsByCategory

data class CategoryStatistics(
    val category: Category?,
    val totalUnits: Double,
    val totalQuantityLiters: Double,
    val drinkCount: Long
) {
    companion object {
        fun fromDb(row: GetStatisticsByCategory): CategoryStatistics =
            CategoryStatistics(
                category = row.category?.let(Category::forName),
                totalUnits = row.totalUnits ?: 0.0,
                totalQuantityLiters = row.totalQuantityLiters ?: 0.0,
                drinkCount = row.drinkCount
            )
    }
}
