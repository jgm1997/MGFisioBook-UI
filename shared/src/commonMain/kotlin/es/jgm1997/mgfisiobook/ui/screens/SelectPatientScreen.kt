package es.jgm1997.mgfisiobook.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import es.jgm1997.mgfisiobook.ui.components.patients.PatientList
import es.jgm1997.mgfisiobook.viewmodels.appointments.CreateAppointmentViewModel
import es.jgm1997.mgfisiobook.viewmodels.patients.PatientsState
import es.jgm1997.mgfisiobook.viewmodels.patients.PatientsViewModel
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
class SelectPatientScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val patientsViewModel = getScreenModel<PatientsViewModel>()
        val appointmentViewModel = getScreenModel<CreateAppointmentViewModel>()
        val state by patientsViewModel.state.collectAsState()

        LaunchedEffect(Unit) {
            patientsViewModel.getAllPatients()
        }

        PatientList(
            patients = (state as PatientsState.Success).patients,
            onSelect = { patient ->
                appointmentViewModel.setPatient(patient.id)
                navigator?.push(ConfirmAppointmentScreen())
            }
        )
    }
}