package es.jgm1997.mgfisiobook.ui.components.availability

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import es.jgm1997.mgfisiobook.core.models.AvailabilityPublic

@Composable
fun AvailabilityList(
    slots: List<AvailabilityPublic>,
    onDelete: (AvailabilityPublic) -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Disponibilidad", style = MaterialTheme.typography.h5)

        Spacer(Modifier.height(16.dp))

        slots.forEach { slot ->
            Card(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("${slot.startTime} -> ${slot.endTime}")
                    Spacer(Modifier.height(8.dp))
                    Button(
                        onClick = { onDelete(slot) },
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error)
                    ) {
                        Text("Eliminar", color = MaterialTheme.colors.onError)
                    }
                }
            }
        }
    }
}