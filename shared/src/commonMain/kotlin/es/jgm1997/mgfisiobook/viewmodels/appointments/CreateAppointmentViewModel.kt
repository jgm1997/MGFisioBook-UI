package es.jgm1997.mgfisiobook.viewmodels.appointments

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import es.jgm1997.mgfisiobook.core.models.AppointmentCreate
import es.jgm1997.mgfisiobook.core.repositories.AppointmentsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class CreateAppointmentViewModel(private val repository: AppointmentsRepository) : ScreenModel {
    private val _state = MutableStateFlow<CreateAppointmentState>(CreateAppointmentState.Idle)
    val state: StateFlow<CreateAppointmentState> = _state

    fun setSlot(startTime: LocalDateTime, endTime: LocalDateTime, therapistId: Uuid) {
        _state.value = CreateAppointmentState.Form(
            therapistId = therapistId,
            startTime = startTime.toInstant(TimeZone.UTC),
            endTime = endTime.toInstant(TimeZone.UTC)
        )
    }

    fun setTreatment(id: Uuid) {
        val current = _state.value
        if (current is CreateAppointmentState.Form) {
            _state.value = current.copy(treatmentId = id)
        }
    }

    fun setPatient(id: Uuid) {
        val current = _state.value
        if (current is CreateAppointmentState.Form) {
            _state.value = current.copy(patientId = id)
        }
    }

    fun create() {
        val current = _state.value
        if (current !is CreateAppointmentState.Form) return

        if (current.treatmentId == null) {
            _state.value = CreateAppointmentState.Error("Selecciona un tratamiento.")
            return
        }

        if (current.patientId == null) {
            _state.value = CreateAppointmentState.Error("Selecciona un paciente.")
            return
        }

        screenModelScope.launch {
            _state.value = CreateAppointmentState.Loading

            try {
                val result = repository.createAppointment(
                    AppointmentCreate(
                        therapistId = current.therapistId,
                        patientID = current.patientId,
                        treatmentId = current.treatmentId,
                        startTime = current.startTime,
                        endTime = current.endTime
                    )
                )
                _state.value = CreateAppointmentState.Success(result.id)
            } catch (e: Exception) {
                _state.value = CreateAppointmentState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}

sealed class CreateAppointmentState {
    object Idle : CreateAppointmentState()
    object Loading : CreateAppointmentState()
    data class Error(val message: String) : CreateAppointmentState()

    @OptIn(ExperimentalUuidApi::class)
    data class Success(val appointmentId: Uuid) : CreateAppointmentState()

    @OptIn(ExperimentalUuidApi::class)
    data class Form(
        val therapistId: Uuid,
        val startTime: Instant,
        val endTime: Instant,
        val treatmentId: Uuid? = null,
        val patientId: Uuid? = null
    ) : CreateAppointmentState()
}