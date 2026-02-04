package es.jgm1997.mgfisiobook.ui.components.datetime

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
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
import kotlinx.datetime.LocalDate

@Composable
fun DateSelector(days: List<LocalDate>, selected: LocalDate, onSelect: (LocalDate) -> Unit) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(days) { day ->
            val isSelected = day == selected
            Surface(
                shape = MaterialTheme.shapes.small,
                color = if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
                modifier = Modifier.padding(end = 8.dp).clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { onSelect(day) }
            ) {
                Text(
                    day.toString(),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    color = if (isSelected) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface
                )
            }
        }
    }
}