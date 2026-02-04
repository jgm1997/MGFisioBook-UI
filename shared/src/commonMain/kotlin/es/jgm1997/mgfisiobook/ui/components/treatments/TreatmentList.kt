package es.jgm1997.mgfisiobook.ui.components.treatments

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
import compose.icons.feathericons.Activity
import es.jgm1997.mgfisiobook.core.models.Treatment

@Composable
fun TreatmentList(
    treatments: List<Treatment>,
    onSelect: (Treatment) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        treatments.forEach { treatment ->
            Card(
                elevation = 4.dp,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }) {
                        onSelect(treatment)
                    }
            ) {
                Row(modifier = Modifier.padding(16.dp)) {
                    Icon(
                        FeatherIcons.Activity,
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary
                    )
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(treatment.name, style = MaterialTheme.typography.h6)
                        if (!treatment.description.isNullOrBlank()) {
                            Spacer(Modifier.height(4.dp))
                            Text(treatment.description, style = MaterialTheme.typography.h6)
                        }
                        Spacer(Modifier.height(4.dp))
                        Text(
                            "Duración: ${treatment.durationMinutes}",
                            style = MaterialTheme.typography.caption
                        )
                    }
                }
            }
        }
    }
}