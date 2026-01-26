package es.jgm1997.mgfisiobook.viewmodels.availability

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import es.jgm1997.mgfisiobook.core.models.AvailabilityPublic
import es.jgm1997.mgfisiobook.core.repositories.AvailabilityRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class MyAvailabilityViewModel(private val repository: AvailabilityRepository) : ScreenModel {
    private val _state = MutableStateFlow<MyAvailabilityState>(MyAvailabilityState.Idle)
    val state: StateFlow<MyAvailabilityState> = _state

    fun load() {
        screenModelScope.launch {
            _state.value = MyAvailabilityState.Loading

            try {
                val items = repository.getMyAvailability()
                _state.value = MyAvailabilityState.Success(items)
            } catch (e: Exception) {
                _state.value = MyAvailabilityState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun delete(id: Uuid) {
        screenModelScope.launch {
            _state.value = MyAvailabilityState.Loading

            try {
                repository.deleteAvailabilitySlot(id)
                val items = repository.getMyAvailability()
                _state.value = MyAvailabilityState.Success(items)
            } catch (e: Exception) {
                _state.value = MyAvailabilityState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}

sealed class MyAvailabilityState {
    object Idle : MyAvailabilityState()
    object Loading : MyAvailabilityState()
    data class Success(val items: List<AvailabilityPublic>) : MyAvailabilityState()
    data class Error(val message: String) : MyAvailabilityState()
}