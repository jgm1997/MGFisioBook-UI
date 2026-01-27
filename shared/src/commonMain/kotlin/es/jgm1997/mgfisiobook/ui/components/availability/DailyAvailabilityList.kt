package es.jgm1997.mgfisiobook.ui.components.availability

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import es.jgm1997.mgfisiobook.core.models.AvailabilitySlot

@Composable
fun DailyAvailabilityList(slots: List<AvailabilitySlot>, onSelect: (AvailabilitySlot) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        slots.forEach { slot ->
            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)
                    .clickable(enabled = slot.available) { onSelect(slot) },
                backgroundColor = if (slot.available)
                    MaterialTheme.colors.primary.copy(0.2f)
                else
                    MaterialTheme.colors.onSurface.copy(0.2f)
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("${slot.start} -> ${slot.end}")
                    if (!slot.available) {
                        Text("No disponible", color = MaterialTheme.colors.error)
                    }
                }
            }
        }
    }
}