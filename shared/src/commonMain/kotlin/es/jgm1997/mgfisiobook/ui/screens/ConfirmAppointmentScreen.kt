package es.jgm1997.mgfisiobook.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import compose.icons.FeatherIcons
import compose.icons.feathericons.Activity
import compose.icons.feathericons.Calendar
import compose.icons.feathericons.Clock
import compose.icons.feathericons.User
import es.jgm1997.mgfisiobook.ui.components.common.AppTopBar
import es.jgm1997.mgfisiobook.ui.components.common.ErrorComponent
import es.jgm1997.mgfisiobook.ui.components.common.LoadingComponent
import es.jgm1997.mgfisiobook.viewmodels.appointments.CreateAppointmentState
import es.jgm1997.mgfisiobook.viewmodels.appointments.CreateAppointmentViewModel
import es.jgm1997.mgfisiobook.viewmodels.patients.PatientsState
import es.jgm1997.mgfisiobook.viewmodels.patients.PatientsViewModel
import es.jgm1997.mgfisiobook.viewmodels.treatments.TreatmentsState
import es.jgm1997.mgfisiobook.viewmodels.treatments.TreatmentsViewModel
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
class ConfirmAppointmentScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val appointmentViewModel = getScreenModel<CreateAppointmentViewModel>()
        val patientsViewModel = getScreenModel<PatientsViewModel>()
        val treatmentsViewModel = getScreenModel<TreatmentsViewModel>()
        val appointmentState by appointmentViewModel.state.collectAsState()
        val patientsState by patientsViewModel.state.collectAsState()
        val treatmentsState by treatmentsViewModel.state.collectAsState()

        // Manejar navegación cuando la cita se crea exitosamente
        // Evitamos el crash del lifecycle navegando solo cuando el estado cambia
        LaunchedEffect(appointmentState) {
            if (appointmentState is CreateAppointmentState.Success) {
                navigator.popAll()
                navigator.push(AppointmentsListScreen())
            }
        }

        // No mostrar nada si ya navegamos
        if (appointmentState is CreateAppointmentState.Success) {
            return
        }

        Column(Modifier.fillMaxSize()) {
            AppTopBar("Confirmar cita")

            when (appointmentState) {
                is CreateAppointmentState.Loading -> LoadingComponent(32.dp)
                is CreateAppointmentState.Error ->
                    ErrorComponent((appointmentState as CreateAppointmentState.Error).message)

                is CreateAppointmentState.Form -> {
                    val form = appointmentState as CreateAppointmentState.Form
                    val patient =
                        (patientsState as PatientsState.Success).patients.find { it.id == form.patientId }
                    val treatment =
                        (treatmentsState as TreatmentsState.Success).treatments.find { it.id == form.treatmentId }
                    val startLocal = form.startTime.toLocalDateTime(TimeZone.UTC)
                    val endLocal = form.endTime.toLocalDateTime((TimeZone.UTC))

                    Column(Modifier.fillMaxWidth().padding(16.dp)) {
                        Row(Modifier.padding(vertical = 8.dp)) {
                            Icon(FeatherIcons.Calendar, contentDescription = null)
                            Spacer(Modifier.width(12.dp))
                            Text("${startLocal.date}", style = MaterialTheme.typography.h6)
                        }

                        Row(Modifier.padding(vertical = 8.dp)) {
                            Icon(FeatherIcons.Clock, contentDescription = null)
                            Spacer(Modifier.width(12.dp))
                            Text(
                                "${startLocal.time} -> ${endLocal.time}",
                                style = MaterialTheme.typography.body1
                            )
                        }

                        Row(Modifier.padding(vertical = 8.dp)) {
                            Icon(FeatherIcons.User, contentDescription = null)
                            Spacer(Modifier.width(12.dp))
                            Text(
                                "${patient?.firstName} ${patient?.lastName}",
                                style = MaterialTheme.typography.body1
                            )
                        }

                        Row(Modifier.padding(vertical = 8.dp)) {
                            Icon(FeatherIcons.Activity, contentDescription = null)
                            Spacer(Modifier.width(12.dp))
                            Text(
                                "${treatment?.name}",
                                style = MaterialTheme.typography.body1
                            )
                        }

                        Spacer(Modifier.height(32.dp))

                        Button(
                            onClick = {
                                appointmentViewModel.create()
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.primary
                            )
                        ) {
                            Text("Confirmar cita", color = MaterialTheme.colors.onPrimary)
                        }
                    }
                }

                else -> Unit
            }
        }
    }
}