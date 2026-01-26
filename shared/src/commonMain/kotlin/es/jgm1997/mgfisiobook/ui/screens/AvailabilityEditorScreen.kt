package es.jgm1997.mgfisiobook.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
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
import es.jgm1997.mgfisiobook.ui.components.common.LoadingComponent
import es.jgm1997.mgfisiobook.viewmodels.availability.AvailabilityEditorState
import es.jgm1997.mgfisiobook.viewmodels.availability.AvailabilityEditorViewModel
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toLocalDateTime

class AvailabilityEditorScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel = getScreenModel<AvailabilityEditorViewModel>()
        val state by viewModel.state.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.reset()
        }

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Crear disponibilidad", style = MaterialTheme.typography.h5)
            Spacer(Modifier.height(16.dp))

            when (state) {
                is AvailabilityEditorState.Loading -> LoadingComponent()
                is AvailabilityEditorState.Form -> {
                    OutlinedTextField(
                        value = (state as AvailabilityEditorState.Form).weekday.toString(),
                        onValueChange = { viewModel.updateWeekday(it.toIntOrNull() ?: 1) },
                        label = { Text("Día (1-7") }
                    )
                    Spacer(Modifier.height(12.dp))
                    OutlinedTextField(
                        value = (state as AvailabilityEditorState.Form).startTime.toString(),
                        onValueChange = { viewModel.updateStartTime(LocalDateTime.parse(it)) },
                        label = { Text("Hora de inicio (HH:MM)") }
                    )
                    Spacer(Modifier.height(12.dp))
                    OutlinedTextField(
                        value = (state as AvailabilityEditorState.Form).endTime.toString(),
                        onValueChange = { viewModel.updateEndTime(LocalDateTime.parse(it)) },
                        label = { Text("Hora de fin (HH:MM)") }
                    )
                    Spacer(Modifier.height(24.dp))
                    Button(onClick = { viewModel.create { navigator?.pop() } }) {
                        Text("Guardar")
                    }
                }

                is AvailabilityEditorState.Error ->
                    ErrorComponent((state as AvailabilityEditorState.Error).message)

                else -> Unit
            }
        }
    }
}