package es.jgm1997.mgfisiobook.ui.components.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun LoadingComponent(padding: Dp) {
    Box(Modifier.fillMaxSize()) {
        CircularProgressIndicator(Modifier.padding(padding))
    }
}