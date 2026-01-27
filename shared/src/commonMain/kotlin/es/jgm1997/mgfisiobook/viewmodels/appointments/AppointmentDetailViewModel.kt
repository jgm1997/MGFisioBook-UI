package es.jgm1997.mgfisiobook.viewmodels.appointments

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import es.jgm1997.mgfisiobook.core.repositories.AppointmentsRepository
import es.jgm1997.mgfisiobook.core.models.Appointment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class AppointmentDetailViewModel(private val repository: AppointmentsRepository) : ScreenModel {
    private val _state = MutableStateFlow<AppointmentDetailState>(AppointmentDetailState.Idle)
    val state: StateFlow<AppointmentDetailState> = _state

    fun load(id: Uuid) {
        _state.value = AppointmentDetailState.Loading

        screenModelScope.launch {
            try {
                val appointment = repository.getAppointment(id)
                _state.value = AppointmentDetailState.Success(appointment)
            } catch (e: Exception) {
                _state.value = AppointmentDetailState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun delete(onSuccess: () -> Unit) {
        val current = _state.value
        if (current !is AppointmentDetailState.Success) return

        screenModelScope.launch {
            _state.value = AppointmentDetailState.Loading

            try {
                repository.deleteAppointment(current.appointment.id)
                onSuccess()
            } catch (e: Exception) {
                _state.value = AppointmentDetailState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}

sealed class AppointmentDetailState {
    object Idle : AppointmentDetailState()
    object Loading : AppointmentDetailState()
    data class Success(val appointment: Appointment) : AppointmentDetailState()
    data class Error(val message: String) : AppointmentDetailState()
}