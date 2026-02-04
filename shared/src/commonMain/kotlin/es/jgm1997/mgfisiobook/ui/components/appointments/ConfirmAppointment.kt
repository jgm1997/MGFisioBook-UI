package es.jgm1997.mgfisiobook.ui.components.appointments

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import es.jgm1997.mgfisiobook.viewmodels.appointments.CreateAppointmentState
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
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
        Text("Terapeuta: ${form.therapistId}")
        Text("Tratamiento: $treatmentName")
        Text("Paciente: $patientName")
        Text("Inicio: ${form.startTime.toLocalDateTime(TimeZone.UTC).time}")
        Text("Fin: ${form.endTime.toLocalDateTime(TimeZone.UTC).time}")

        Spacer(Modifier.height(32.dp))

        Button(onConfirm) {
            Text("Confirmar cita")
        }
    }
}