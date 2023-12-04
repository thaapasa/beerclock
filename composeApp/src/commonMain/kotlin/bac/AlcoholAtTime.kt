package fi.tuska.beerclock.bac

import kotlinx.datetime.Instant

/**
 * Records what the blood alcohol level was at a given time, as grams of alcohol
 * left for your liver to burn.
 */
data class AlcoholAtTime(val time: Instant, val alcoholGrams: Double)
