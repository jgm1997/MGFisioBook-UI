package es.jgm1997.mgfisiobook.ui.components.patients

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import es.jgm1997.mgfisiobook.core.models.Patient

@Composable
fun PatientList(patients: List<Patient>, onSelect: (Patient) -> Unit) {
    Column(Modifier.fillMaxSize()) {
        patients.forEach { patient ->
            Card(
                Modifier.fillMaxWidth().padding(vertical = 6.dp)
                    .clickable { onSelect(patient) }) {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        "${patient.firstName} ${patient.lastName}",
                        style = MaterialTheme.typography.h6
                    )
                    Spacer(Modifier.height(4.dp))
                    Text("Email: ${patient.email}")
                }
            }
        }
    }
}