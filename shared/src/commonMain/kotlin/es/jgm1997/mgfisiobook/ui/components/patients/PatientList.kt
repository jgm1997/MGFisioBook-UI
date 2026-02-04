package es.jgm1997.mgfisiobook.ui.components.patients

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import compose.icons.FeatherIcons
import compose.icons.feathericons.User
import es.jgm1997.mgfisiobook.core.models.Patient
import es.jgm1997.mgfisiobook.ui.components.common.AppTopBar
import es.jgm1997.mgfisiobook.ui.components.common.EmptyState

@Composable
fun PatientList(patients: List<Patient>, onSelect: (Patient) -> Unit) {
    if (patients.isEmpty()) {
        EmptyState("No hay pacientes")
    } else {
        Column(Modifier.fillMaxSize()) {
            AppTopBar("Selecciona paciente")

            patients.forEach { patient ->
                Card(
                    elevation = 4.dp, shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                        ) { onSelect(patient) }
                ) {
                    Row(Modifier.padding(16.dp)) {
                        Icon(
                            FeatherIcons.User,
                            contentDescription = null,
                            tint = MaterialTheme.colors.primary
                        )
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Text(
                                "${patient.firstName} ${patient.lastName}",
                                style = MaterialTheme.typography.h6
                            )
                            Spacer(Modifier.height(4.dp))
                            Text("Email: ${patient.email}", style = MaterialTheme.typography.body2)
                        }
                    }
                }
            }
        }
    }
}