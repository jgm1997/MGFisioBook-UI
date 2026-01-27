package es.jgm1997.mgfisiobook.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import es.jgm1997.mgfisiobook.ui.components.appointments.AppointmentDetailContent
import es.jgm1997.mgfisiobook.ui.components.common.ErrorComponent
import es.jgm1997.mgfisiobook.viewmodels.appointments.AppointmentDetailState
import es.jgm1997.mgfisiobook.viewmodels.appointments.AppointmentDetailViewModel
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class AppointmentDetailScreen(val appointmentId: Uuid) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel = getScreenModel<AppointmentDetailViewModel>()
        val state by viewModel.state.collectAsState()
        val showDialog = mutableStateOf(false)

        LaunchedEffect(appointmentId) {
            viewModel.load(appointmentId)
        }

        when (state) {
            is AppointmentDetailState.Loading -> {
                Box(Modifier.fillMaxSize()) {
                    CircularProgressIndicator(Modifier.padding(32.dp))
                }
            }

            is AppointmentDetailState.Success -> {
                Column(Modifier.fillMaxSize().padding(16.dp)) {
                    AppointmentDetailContent((state as AppointmentDetailState.Success).appointment)
                    Spacer(Modifier.height(24.dp))
                    Button(
                        onClick = { showDialog.value = true }, colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.error
                        )
                    ) {
                        Text("Cancelar cita")
                    }
                    Spacer(Modifier.height(24.dp))
                    Button(onClick = { navigator?.replace(HomeScreen()) }) {
                        Text("Ir a Inicio")
                    }
                }

                if (showDialog.value) {
                    AlertDialog(
                        onDismissRequest = { showDialog.value = false },
                        title = { Text("Cancelar cita") },
                        text = { Text("¿Desea cancelar la cita?") },
                        confirmButton = {
                            TextButton(onClick = { viewModel.delete { navigator?.pop() } }) {
                                Text("Sí, cancelar")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showDialog.value = false }) {}
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