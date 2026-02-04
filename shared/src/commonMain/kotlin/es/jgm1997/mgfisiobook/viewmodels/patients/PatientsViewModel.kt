package es.jgm1997.mgfisiobook.viewmodels.patients

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import es.jgm1997.mgfisiobook.core.models.Patient
import es.jgm1997.mgfisiobook.core.repositories.PatientRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
class PatientsViewModel(private val repository: PatientRepository) : ScreenModel {
    private val _state = MutableStateFlow<PatientsState>(PatientsState.Loading)
    val state: StateFlow<PatientsState> = _state

    fun getAllPatients() {
        screenModelScope.launch {
            _state.value = PatientsState.Loading
            try {
                val result = repository.getAllPatients()
                _state.value = PatientsState.Success(result)
            } catch (e: Exception) {
                _state.value = PatientsState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}

sealed class PatientsState {
    object Idle : PatientsState()
    object Loading : PatientsState()
    data class Error(val message: String) : PatientsState()
    data class Success(val patients: List<Patient>) : PatientsState()
}