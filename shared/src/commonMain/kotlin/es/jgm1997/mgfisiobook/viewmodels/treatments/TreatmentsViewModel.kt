package es.jgm1997.mgfisiobook.viewmodels.treatments

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import es.jgm1997.mgfisiobook.core.models.Treatment
import es.jgm1997.mgfisiobook.core.repositories.TreatmentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TreatmentsViewModel(private val repository: TreatmentRepository) : ScreenModel {
    private val _state = MutableStateFlow<TreatmentsState>(TreatmentsState.Loading)
    val state: StateFlow<TreatmentsState> = _state

    init {
        loadTreatments()
    }

    private fun loadTreatments() {
        screenModelScope.launch {
            try {
                val treatments = repository.getTreatments()
                _state.value = TreatmentsState.Success(treatments)
            } catch (e: Exception) {
                _state.value = TreatmentsState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}

sealed class TreatmentsState {
    object Loading : TreatmentsState()
    data class Success(val treatments: List<Treatment>) : TreatmentsState()
    data class Error(val message: String) : TreatmentsState()
}