package es.jgm1997.mgfisiobook.ui.components.patients

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import es.jgm1997.mgfisiobook.core.models.Patient

@Composable
fun PatientList(patients: List<Patient>, onSelect: (Patient) -> Unit) {
    Column(Modifier.fillMaxSize()) {
        patients.forEach { patient ->
            Card(
                elevation = 4.dp, shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                    ) { onSelect(patient) }) {
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