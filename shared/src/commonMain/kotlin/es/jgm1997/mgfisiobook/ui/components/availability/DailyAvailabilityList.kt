package es.jgm1997.mgfisiobook.ui.components.availability

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import compose.icons.feathericons.Clock
import es.jgm1997.mgfisiobook.core.models.AvailabilitySlot
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun DailyAvailabilityList(slots: List<AvailabilitySlot>, onSelect: (AvailabilitySlot) -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        slots.forEach { slot ->
            val startLocal = slot.start.toLocalDateTime(TimeZone.UTC)
            val endLocal = slot.end.toLocalDateTime(TimeZone.UTC)

            Card(
                elevation = 4.dp,
                shape = RoundedCornerShape(12.dp),
                backgroundColor = if (slot.available)
                    MaterialTheme.colors.primary.copy(0.08f)
                else MaterialTheme.colors.onSurface.copy(0.03f),
                modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)
                    .clickable(
                        enabled = slot.available,
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }) {
                        onSelect(slot)
                    }
            ) {
                Row(Modifier.padding(16.dp)) {
                    Icon(
                        FeatherIcons.Clock,
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary
                    )
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(
                            "${startLocal.time} -> ${endLocal.time}",
                            style = MaterialTheme.typography.subtitle1
                        )
                        if (!slot.available) {
                            Text(
                                "No disponible",
                                color = MaterialTheme.colors.error,
                                style = MaterialTheme.typography.subtitle2
                            )
                        }
                    }
                }
            }
        }
    }
}