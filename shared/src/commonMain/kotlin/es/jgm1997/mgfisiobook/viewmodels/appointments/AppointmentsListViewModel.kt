package es.jgm1997.mgfisiobook.viewmodels.appointments

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import es.jgm1997.mgfisiobook.core.models.AppointmentPublic
import es.jgm1997.mgfisiobook.core.repositories.AppointmentsRepository
import es.jgm1997.mgfisiobook.core.repositories.PatientRepository
import es.jgm1997.mgfisiobook.core.repositories.TherapistRepository
import es.jgm1997.mgfisiobook.core.repositories.TreatmentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi

sealed class AppointmentsListState {
    object Idle : AppointmentsListState()
    object Loading : AppointmentsListState()
    data class Success(val appointments: List<AppointmentPublic>) : AppointmentsListState()
    data class Error(val message: String) : AppointmentsListState()
}

class AppointmentsListViewModel(
    private val appointmentsRepository: AppointmentsRepository,
    private val patientRepository: PatientRepository,
    private val therapistRepository: TherapistRepository,
    private val treatmentRepository: TreatmentRepository
) : ScreenModel {
    private val _state = MutableStateFlow<AppointmentsListState>(AppointmentsListState.Idle)
    val state: StateFlow<AppointmentsListState> = _state

    @OptIn(ExperimentalUuidApi::class)
    fun load() {
        screenModelScope.launch {
            _state.value = AppointmentsListState.Loading
            val appointmentsList = mutableListOf<AppointmentPublic>()
            try {
                val appointments = appointmentsRepository.getAppointments()
                for (appointment in appointments) {
                    val patient = patientRepository.getPatient(appointment.patientId)
                    val therapist = therapistRepository.getTherapist(appointment.therapistId)
                    val treatment = treatmentRepository.getTreatment(appointment.treatmentId)
                    appointmentsList += AppointmentPublic(
                        appointment.id,
                        patient,
                        therapist,
                        treatment,
                        appointment.startTime,
                        appointment.endTime,
                        appointment.status,
                        appointment.notes
                    )
                }
                _state.value = AppointmentsListState.Success(appointmentsList)
            } catch (e: Exception) {
                _state.value = AppointmentsListState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}