package es.jgm1997.mgfisiobook.ui.components.common

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoadingComponent() {
    Spacer(Modifier.height(16.dp))
    CircularProgressIndicator()
}