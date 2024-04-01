package fi.tuska.beerclock.events

import fi.tuska.beerclock.drinks.DrinkRecordInfo

open class Event {
    var isConsumed: Boolean = false
        private set

    fun consume() {
        isConsumed = true
    }
}

data class DrinkRecordAddedEvent(val drink: DrinkRecordInfo) : Event()
data class DrinkRecordUpdatedEvent(val drink: DrinkRecordInfo) : Event()
data class DrinkRecordDeletedEvent(val drink: DrinkRecordInfo) : Event()
