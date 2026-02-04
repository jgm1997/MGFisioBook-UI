package es.jgm1997.mgfisiobook.ui.components.appointments

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import es.jgm1997.mgfisiobook.core.models.AppointmentPublic
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
@Composable
fun AppointmentDetailContent(appointment: AppointmentPublic, treatmentName: String? = null) {
    Text("Detalle de la cita", style = MaterialTheme.typography.h5)

    Spacer(Modifier.height(6.dp))
    Divider(Modifier.height(4.dp))
    Spacer(Modifier.height(6.dp))

    Text("Paciente: ${appointment.patient.id}")
    if (treatmentName != null) {
        Text("Tratamiento: $treatmentName")
    } else {
        Text("Tratamiento: ${appointment.treatment.id}")
    }
    Text("Inicio: ${appointment.startTime}")
    Text("Fin: ${appointment.endTime}")
    Text("Estado: ${appointment.status}")
}