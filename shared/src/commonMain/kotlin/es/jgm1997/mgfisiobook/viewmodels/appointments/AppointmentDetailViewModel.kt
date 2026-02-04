package es.jgm1997.mgfisiobook.viewmodels.appointments

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import es.jgm1997.mgfisiobook.core.repositories.AppointmentsRepository
import es.jgm1997.mgfisiobook.core.models.AppointmentPublic
import es.jgm1997.mgfisiobook.core.repositories.PatientRepository
import es.jgm1997.mgfisiobook.core.repositories.TherapistRepository
import es.jgm1997.mgfisiobook.core.repositories.TreatmentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class AppointmentDetailViewModel(
    private val appointmentsRepository: AppointmentsRepository,
    private val patientRepository: PatientRepository,
    private val therapistRepository: TherapistRepository,
    private val treatmentRepository: TreatmentRepository
) : ScreenModel {
    private val _state = MutableStateFlow<AppointmentDetailState>(AppointmentDetailState.Idle)
    val state: StateFlow<AppointmentDetailState> = _state

    fun load(id: Uuid) {
        _state.value = AppointmentDetailState.Loading
        screenModelScope.launch {
            try {
                val appointment = appointmentsRepository.getAppointment(id)
                val patient = patientRepository.getPatient(appointment.patientId)
                val therapist = therapistRepository.getTherapist(appointment.therapistId)
                val treatment = treatmentRepository.getTreatment(appointment.treatmentId)
                _state.value = AppointmentDetailState.Success(
                    AppointmentPublic(
                        appointment.id,
                        patient,
                        therapist,
                        treatment,
                        appointment.startTime,
                        appointment.endTime,
                        appointment.status,
                        appointment.notes
                    )
                )
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
                appointmentsRepository.deleteAppointment(current.appointment.id)
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
    data class Success(val appointment: AppointmentPublic) : AppointmentDetailState()
    data class Error(val message: String) : AppointmentDetailState()
}