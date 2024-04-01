package fi.tuska.beerclock.events


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class EventBus {
    private val _events = MutableSharedFlow<Event>(extraBufferCapacity = 10, replay = 5)
    val events = _events.asSharedFlow()

    suspend fun post(event: Event) {
        _events.emit(event)
    }
}


class EventObserver(val coroutineScope: CoroutineScope) : KoinComponent {
    val eventBus: EventBus by inject()

    // Generic function to observe specific event types
    inline fun <reified T : Event> observeEventsOfType(
        consumeEvent: Boolean = true,
        crossinline onEvent: (T) -> Unit,
    ) {
        coroutineScope.launch {
            eventBus.events
                .filterIsInstance<T>()
                .filter { !it.isConsumed }
                .collect { event ->
                    onEvent(event)
                    if (consumeEvent) {
                        event.consume()
                    }
                }
        }
    }
}
