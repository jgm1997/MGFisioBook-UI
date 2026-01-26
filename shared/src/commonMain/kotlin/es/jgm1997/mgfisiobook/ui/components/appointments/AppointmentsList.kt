@file:OptIn(ExperimentalUuidApi::class)

package es.jgm1997.mgfisiobook.ui.components.appointments

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import es.jgm1997.mgfisiobook.core.models.Appointment
import kotlin.uuid.ExperimentalUuidApi

@Composable
fun AppointmentsList(items: List<Appointment>, onSelect: (Appointment) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Mis citas", style = MaterialTheme.typography.h5)
        Spacer((Modifier.height(16.dp)))

        items.forEach { appointment ->
            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)
                    .clickable { onSelect(appointment) }) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Paciente: ${appointment.patientID}")
                    Text("Tratamiento: ${appointment.treatmentId}")
                    Text("Inicio: ${appointment.startTime}")
                }
            }
        }
    }
}