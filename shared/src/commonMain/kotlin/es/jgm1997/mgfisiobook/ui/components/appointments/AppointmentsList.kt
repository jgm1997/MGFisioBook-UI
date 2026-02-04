@file:OptIn(ExperimentalUuidApi::class)

package es.jgm1997.mgfisiobook.ui.components.appointments

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import compose.icons.FeatherIcons
import compose.icons.feathericons.Calendar
import es.jgm1997.mgfisiobook.core.models.AppointmentPublic
import es.jgm1997.mgfisiobook.ui.components.common.AppTopBar
import es.jgm1997.mgfisiobook.ui.components.common.EmptyState
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.uuid.ExperimentalUuidApi

@Composable
fun AppointmentsList(items: List<AppointmentPublic>, onSelect: (AppointmentPublic) -> Unit) {
    if (items.isEmpty()) {
        EmptyState("No tienes citas")
    } else {
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppTopBar("Mis citas")

            items.forEach { appointment ->
                val startLocal = appointment.startTime.toLocalDateTime(TimeZone.UTC)
                val endLocal = appointment.endTime.toLocalDateTime(TimeZone.UTC)

                Card(
                    elevation = 4.dp, shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                        ) { onSelect(appointment) }) {
                    Row(Modifier.padding(16.dp)) {
                        Icon(
                            FeatherIcons.Calendar,
                            contentDescription = null,
                            tint = MaterialTheme.colors.primary
                        )
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Text(appointment.treatment.name, style = MaterialTheme.typography.h6)
                            Spacer(Modifier.height(4.dp))
                            Text(
                                "${startLocal.date} ${startLocal.time} -> ${endLocal.date} ${endLocal.time}",
                                style = MaterialTheme.typography.body2
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                "Paciente: ${appointment.patient.firstName} ${appointment.patient.lastName}",
                                style = MaterialTheme.typography.caption
                            )
                        }
                    }
                }
            }
        }
    }
}