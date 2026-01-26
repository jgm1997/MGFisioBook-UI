package es.jgm1997.mgfisiobook.viewmodels.availability

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import es.jgm1997.mgfisiobook.core.models.AvailabilitySlot
import es.jgm1997.mgfisiobook.core.repositories.AvailabilityRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class DailyAvailabilityViewModel(private val repository: AvailabilityRepository) : ScreenModel {
    private val _state = MutableStateFlow<DailyAvailabilityState>(DailyAvailabilityState.Idle)
    val state: StateFlow<DailyAvailabilityState> = _state

    fun load(date: LocalDate, therapistId: Uuid) {
        screenModelScope.launch {
            _state.value = DailyAvailabilityState.Loading
            try {
                val slots = repository.getDailyAvailability(date, therapistId)
                _state.value = DailyAvailabilityState.Success(slots)
            } catch (e: Exception) {
                _state.value = DailyAvailabilityState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}

sealed class DailyAvailabilityState {
    object Idle : DailyAvailabilityState()
    object Loading : DailyAvailabilityState()
    data class Success(val data: List<AvailabilitySlot>) : DailyAvailabilityState()
    data class Error(val message: String) : DailyAvailabilityState()
}