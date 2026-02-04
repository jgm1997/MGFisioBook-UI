package es.jgm1997.mgfisiobook.ui.components.datetime

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TimePickerSheet(
    hours: List<String>,
    minutes: List<String>,
    onSelect: (String, String) -> Unit
) {
    Column(Modifier.fillMaxWidth().padding(16.dp)) {
        Text("Seleccionar hora", style = MaterialTheme.typography.h6)
        Spacer(Modifier.height(16.dp))

        Text("Horas", style = MaterialTheme.typography.subtitle1)
        Spacer(Modifier.height(8.dp))

        LazyRow {
            items(hours) { hour ->
                Surface(
                    shape = MaterialTheme.shapes.small,
                    color = MaterialTheme.colors.surface,
                    modifier = Modifier.padding(8.dp).clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { onSelect(hour, "00") }
                ) {
                    Text(
                        hour,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        style = MaterialTheme.typography.body1
                    )
                }
            }
        }
        Spacer(Modifier.height(24.dp))

        Text("Minutos", style = MaterialTheme.typography.subtitle1)
        Spacer(Modifier.height(8.dp))

        LazyRow {
            items(minutes) { minute ->
                Surface(
                    shape = MaterialTheme.shapes.small,
                    color = MaterialTheme.colors.surface,
                    modifier = Modifier.padding(8.dp).clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { onSelect("00", minute) }
                ) {
                    Text(
                        minute,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        style = MaterialTheme.typography.body1
                    )
                }
            }
        }
    }
}