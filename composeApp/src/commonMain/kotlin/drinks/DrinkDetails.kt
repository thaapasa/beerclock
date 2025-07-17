package fi.tuska.beerclock.drinks

import kotlin.time.Instant

data class DrinkDetails(
    val timesDrunk: Long,
    val quantityLiters: Double,
    val firstTimeDrunk: Instant?,
    val lastTimeDrunk: Instant?,
)

data class DrinkNote(val time: Instant, val note: String?, val rating: Double?)
