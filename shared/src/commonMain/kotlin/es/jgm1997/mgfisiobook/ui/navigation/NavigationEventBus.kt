@file:OptIn(ExperimentalUuidApi::class)

package es.jgm1997.mgfisiobook.ui.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

sealed class NavigationEvent {
    data class ToAppointmentDetail(val id: Uuid) : NavigationEvent()
}

object NavigationEventBus {
    private val _events = MutableSharedFlow<NavigationEvent>()
    val events: SharedFlow<NavigationEvent> = _events

    suspend fun send(event: NavigationEvent) {
        _events.emit(event)
    }
}