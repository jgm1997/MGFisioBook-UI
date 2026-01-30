package es.jgm1997.mgfisiobook.ui.components.availability

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import es.jgm1997.mgfisiobook.core.models.AvailabilityPublic

@Composable
fun MyAvailabilityList(items: List<AvailabilityPublic>) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Mi disponibilidad", style = MaterialTheme.typography.h5)
        Spacer(Modifier.height(6.dp))
        Divider(Modifier.height(4.dp))
        Spacer(Modifier.height(6.dp))

        items.forEach { item ->
            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                elevation = 4.dp,
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("Día: ${item.weekday}")
                    Text("Inicio: ${item.startTime}")
                    Text("Fin: ${item.endTime}")
                }
            }
        }
    }
}