package es.jgm1997.mgfisiobook.ui.components.common

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun ErrorComponent(message: String) {
    Text(
        text = message,
        color = MaterialTheme.colors.error
    )
}