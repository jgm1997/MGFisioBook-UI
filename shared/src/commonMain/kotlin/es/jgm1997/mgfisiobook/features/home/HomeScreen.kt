package es.jgm1997.mgfisiobook.features.home

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

class HomeScreen: Screen {
    @Composable
    override fun Content() {
        Text("Bienvenidos a MGFisioBook")
    }
}