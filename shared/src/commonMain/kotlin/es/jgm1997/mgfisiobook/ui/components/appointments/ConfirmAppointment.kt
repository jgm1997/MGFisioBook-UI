package es.jgm1997.mgfisiobook.ui.components.appointments

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import es.jgm1997.mgfisiobook.viewmodels.appointments.CreateAppointmentState
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
@Composable
fun ConfirmAppointment(
    form: CreateAppointmentState.Form,
    treatmentName: String,
    patientName: String,
    onConfirm: () -> Unit
) {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Confirmar cita", style = MaterialTheme.typography.h5)
        Spacer(Modifier.height(16.dp))

        Text("Terapeuta: ${form.therapistId}")
        Text("Tratamiento: $treatmentName")
        Text("Paciente: $patientName")
        Text("Inicio: ${form.startTime}")
        Text("Fin: ${form.endTime}")
        Spacer(Modifier.height(24.dp))

        Button(onConfirm) {
            Text("Confirmar cita")
        }
    }
}