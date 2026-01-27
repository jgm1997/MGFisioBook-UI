package es.jgm1997.mgfisiobook.ui.components.treatments

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
import es.jgm1997.mgfisiobook.core.models.Treatment

@Composable
fun TreatmentList(
    treatments: List<Treatment>,
    onSelect: (Treatment) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        treatments.forEach { treatment ->
            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)
                    .clickable { onSelect(treatment) }) {
                Column(Modifier.padding(16.dp)) {
                    Text(treatment.name, style = MaterialTheme.typography.h6)
                    Spacer(Modifier.height(4.dp))
                    Text(treatment.description ?: "")
                    Spacer(Modifier.height(4.dp))
                    Text("Duración: ${treatment.durationMinutes} min")
                }
            }
        }
    }
}