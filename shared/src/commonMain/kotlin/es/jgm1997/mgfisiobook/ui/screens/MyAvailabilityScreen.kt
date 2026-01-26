package es.jgm1997.mgfisiobook.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import es.jgm1997.mgfisiobook.viewmodels.availability.MyAvailabilityState
import es.jgm1997.mgfisiobook.viewmodels.availability.MyAvailabilityViewModel

class MyAvailabilityScreen() : Screen {
    @Composable
    override fun Content() {
        val viewModel = getScreenModel<MyAvailabilityViewModel>()
        val state by viewModel.state.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.load()
        }

        when (state) {
            is MyAvailabilityState.Loading -> {
                Box(Modifier.fillMaxSize()) {
                    CircularProgressIndicator(Modifier.padding(32.dp))
                }
            }

            is MyAvailabilityState.Success -> {
                val items = (state as MyAvailabilityState.Success).items

                // TODO: Mostrar la lista de items
            }
            is MyAvailabilityState.Error -> {
                Text(
                    text = (state as MyAvailabilityState.Error).message,
                    color = MaterialTheme.colors.error
                )
            }

            else -> Unit
        }
    }
}