package es.jgm1997.mgfisiobook.ui.components.appointments

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import es.jgm1997.mgfisiobook.core.models.Appointment
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
@Composable
fun AppointmentDetailContent(appointment: Appointment, treatmentName: String? = null) {
    Text("Detalle de la cita", style = MaterialTheme.typography.h5)
    Spacer(Modifier.height(16.dp))

    Text("Paciente: ${appointment.patientID}")
    if (treatmentName != null) {
        Text("Tratamiento: $treatmentName")
    } else {
        Text("Tratamiento: ${appointment.treatmentId}")
    }
    Text("Inicio: ${appointment.startTime}")
    Text("Fin: ${appointment.endTime}")
    Text("Estado: ${appointment.status}")
}