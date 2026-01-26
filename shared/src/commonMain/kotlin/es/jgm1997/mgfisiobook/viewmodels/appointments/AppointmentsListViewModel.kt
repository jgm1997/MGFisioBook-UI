package es.jgm1997.mgfisiobook.viewmodels.appointments

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import es.jgm1997.mgfisiobook.core.models.Appointment
import es.jgm1997.mgfisiobook.core.repositories.AppointmentsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.logger.MESSAGE

sealed class AppointmentsListState {
    object Idle : AppointmentsListState()
    object Loading : AppointmentsListState()
    data class Success(val appointments: List<Appointment>) : AppointmentsListState()
    data class Error(val message: String) : AppointmentsListState()
}

class AppointmentsListViewModel(private val repository: AppointmentsRepository) : ScreenModel {
    private val _state = MutableStateFlow<AppointmentsListState>(AppointmentsListState.Idle)
    val state: StateFlow<AppointmentsListState> = _state

    fun load() {
        screenModelScope.launch {
            _state.value = AppointmentsListState.Loading
            try {
                val appointments = repository.getAppointments()
                _state.value = AppointmentsListState.Success(appointments)
            } catch (e: Exception) {
                _state.value = AppointmentsListState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}