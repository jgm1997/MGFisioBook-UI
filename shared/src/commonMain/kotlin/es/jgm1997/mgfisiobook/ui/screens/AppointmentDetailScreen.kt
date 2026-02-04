package es.jgm1997.mgfisiobook.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import compose.icons.FeatherIcons
import compose.icons.feathericons.Activity
import compose.icons.feathericons.Calendar
import compose.icons.feathericons.Clock
import compose.icons.feathericons.User
import es.jgm1997.mgfisiobook.ui.components.appointments.AppointmentDetailContent
import es.jgm1997.mgfisiobook.ui.components.common.AppTopBar
import es.jgm1997.mgfisiobook.ui.components.common.ErrorComponent
import es.jgm1997.mgfisiobook.ui.components.common.LoadingComponent
import es.jgm1997.mgfisiobook.viewmodels.appointments.AppointmentDetailState
import es.jgm1997.mgfisiobook.viewmodels.appointments.AppointmentDetailViewModel
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class AppointmentDetailScreen(val appointmentId: Uuid) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel = getScreenModel<AppointmentDetailViewModel>()
        val state by viewModel.state.collectAsState()

        var showDialog by remember { mutableStateOf(false) }

        LaunchedEffect(appointmentId) {
            viewModel.load(appointmentId)
        }

        Column(Modifier.fillMaxSize()) {
            AppTopBar("Detalle de la cita")


            when (state) {
                is AppointmentDetailState.Loading -> LoadingComponent(32.dp)

                is AppointmentDetailState.Success -> {
                    val appointment = (state as AppointmentDetailState.Success).appointment
                    val startLocal = appointment.startTime.toLocalDateTime(TimeZone.UTC)
                    val endLocal = appointment.endTime.toLocalDateTime(TimeZone.UTC)

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
                                "${appointment.patient.firstName} ${appointment.patient.lastName}",
                                style = MaterialTheme.typography.body1
                            )
                        }

                        Row(Modifier.padding(vertical = 8.dp)) {
                            Icon(FeatherIcons.Activity, contentDescription = null)
                            Spacer(Modifier.width(12.dp))
                            Text(
                                appointment.treatment.name,
                                style = MaterialTheme.typography.body1
                            )
                        }

                        Spacer(Modifier.height(32.dp))

                        Button(
                            onClick = { showDialog = true },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.error
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Cancelar cita", color = MaterialTheme.colors.onError)
                        }
                    }

                    if (showDialog) {
                        AlertDialog(
                            onDismissRequest = { showDialog = false },
                            title = { Text("Cancelar cita") },
                            text = { Text("¿Estás seguro que quieres cancelar la cita?") },
                            confirmButton = {
                                TextButton(onClick = { viewModel.delete { navigator?.pop() } }) {
                                    Text("Sí, cancelar")
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = { showDialog = false }) { Text("No") }
                            }
                        )
                    }
                }

                is AppointmentDetailState.Error ->
                    ErrorComponent((state as AppointmentDetailState.Error).message)

                else -> Unit
            }
        }
    }
}