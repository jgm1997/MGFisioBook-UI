package es.jgm1997.mgfisiobook.features.treatments

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import es.jgm1997.mgfisiobook.core.models.TreatmentCreate
import es.jgm1997.mgfisiobook.core.treatments.TreatmentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CreateTreatmentViewModel(private val repository: TreatmentRepository) : ScreenModel {
    private val _state = MutableStateFlow<CreateTreatmentState>(CreateTreatmentState.Idle)
    val state: StateFlow<CreateTreatmentState> = _state

    fun create(name: String, description: String, durationMinutes: Int, price: Double) {
        _state.value = CreateTreatmentState.Loading


        screenModelScope.launch {
            try {
                repository.createTreatment(
                    TreatmentCreate(
                        name,
                        description,
                        durationMinutes,
                        price
                    )
                )
                _state.value = CreateTreatmentState.Success
            } catch (e: Exception) {
                _state.value = CreateTreatmentState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}

sealed class CreateTreatmentState {
    object Idle : CreateTreatmentState()
    object Loading : CreateTreatmentState()
    object Success : CreateTreatmentState()
    data class Error(val message: String) : CreateTreatmentState()
}