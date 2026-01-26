package es.jgm1997.mgfisiobook.viewmodels.availability

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import es.jgm1997.mgfisiobook.core.models.AvailabilityCreate
import es.jgm1997.mgfisiobook.core.repositories.AvailabilityRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime

sealed class AvailabilityEditorState {
    object Idle : AvailabilityEditorState()
    object Loading : AvailabilityEditorState()
    data class Form(
        val weekday: Int = 1,
        val startTime: LocalDateTime? = null,
        val endTime: LocalDateTime? = null
    ) : AvailabilityEditorState()

    data class Error(val message: String) : AvailabilityEditorState()
}

class AvailabilityEditorViewModel(
    private val repository: AvailabilityRepository
) : ScreenModel {
    private val _state = MutableStateFlow<AvailabilityEditorState>(AvailabilityEditorState.Idle)
    val state: StateFlow<AvailabilityEditorState> = _state

    fun updateWeekday(value: Int) {
        val current = _state.value
        if (current is AvailabilityEditorState.Form) {
            _state.value = current.copy(weekday = value)
        }
    }

    fun updateStartTime(value: LocalDateTime) {
        val current = _state.value
        if (current is AvailabilityEditorState.Form) {
            _state.value = current.copy(startTime = value)
        }
    }

    fun updateEndTime(value: LocalDateTime) {
        val current = _state.value
        if (current is AvailabilityEditorState.Form) {
            _state.value = current.copy(endTime = value)
        }
    }

    fun create(onSuccess: () -> Unit) {
        val current = _state.value
        if (current !is AvailabilityEditorState.Form) return
        if (current.weekday !in 1..7) {
            _state.value =
                AvailabilityEditorState.Error("El día de la semana debe estar entre 1 y 7")
            return
        }
        if (current.startTime == null || current.endTime == null) {
            _state.value =
                AvailabilityEditorState.Error("Debes seleccionar una hora de inicio y de final")
            return
        }

        screenModelScope.launch {
            _state.value = AvailabilityEditorState.Loading

            try {
                repository.createAvailability(
                    AvailabilityCreate(
                        weekday = current.weekday,
                        startTime = current.startTime,
                        endTime = current.endTime
                    )
                )
                onSuccess()
            } catch (e: Exception) {
                _state.value = AvailabilityEditorState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun reset() {
        _state.value = AvailabilityEditorState.Form()
    }
}