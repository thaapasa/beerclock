package fi.tuska.beerclock.events

import fi.tuska.beerclock.drinks.DrinkInfo
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

data class DrinkInfoAddedEvent(val drink: DrinkInfo) : Event()
data class DrinkInfoUpdatedEvent(val drink: DrinkInfo) : Event()
data class DrinkInfoDeletedEvent(val drink: DrinkInfo) : Event()
