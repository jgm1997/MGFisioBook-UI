package es.jgm1997.mgfisiobook.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import es.jgm1997.mgfisiobook.ui.components.common.ErrorComponent
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
                val navigator = LocalNavigator.current

                Column(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column {
                        items.forEach { item ->
                            Text("Día: ${item.weekday} -> ${item.startTime} - ${item.endTime}")
                            Spacer(Modifier.height(8.dp))
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                    Button(onClick = { navigator?.push(AvailabilityEditorScreen()) }) {
                        Text("Añadir disponibilidad")
                    }
                }
            }

            is MyAvailabilityState.Error ->
                ErrorComponent((state as MyAvailabilityState.Error).message)

            else -> Unit
        }
    }
}