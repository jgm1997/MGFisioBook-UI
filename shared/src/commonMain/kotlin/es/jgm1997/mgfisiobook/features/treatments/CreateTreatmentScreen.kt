package es.jgm1997.mgfisiobook.features.treatments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import es.jgm1997.mgfisiobook.core.auth.hasRole
import es.jgm1997.mgfisiobook.core.auth.isAuthenticated
import es.jgm1997.mgfisiobook.features.home.HomeScreen
import es.jgm1997.mgfisiobook.features.login.LoginScreen

class CreateTreatmentScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel = getScreenModel<CreateTreatmentViewModel>()
        val state by viewModel.state.collectAsState()

        var name by remember { mutableStateOf("") }
        var description by remember { mutableStateOf("") }
        var durationMinutes by remember { mutableStateOf("") }
        var price by remember { mutableStateOf("") }

        if (!isAuthenticated()) {
            navigator?.replace(LoginScreen())
            return
        }

        if (!hasRole("admin", "therapist")) {
            navigator?.replace(HomeScreen())
            return
        }

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Crear tratamiento", style = MaterialTheme.typography.h5)

            Spacer(Modifier.height(16.dp))

            TextField(value = name, onValueChange = { name = it }, label = { Text("Nombre") })
            Spacer(Modifier.height(12.dp))

            TextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción") })
            Spacer(Modifier.height(12.dp))

            TextField(
                value = durationMinutes,
                onValueChange = { durationMinutes = it },
                label = { Text("Duración (minutos)") })
            Spacer(Modifier.height(12.dp))

            TextField(value = price, onValueChange = { price = it }, label = { Text("Precio (€)") })
            Spacer(Modifier.height(16.dp))

            Button(onClick = {
                viewModel.create(
                    name, description,
                    durationMinutes.toInt(), price.toDouble()
                )
            }) {
                Text("Crear")
            }

            when (state) {
                is CreateTreatmentState.Loading -> {
                    Spacer(Modifier.height(16.dp))
                    CircularProgressIndicator()
                }

                is CreateTreatmentState.Success -> {
                    navigator?.replace(TreatmentsScreen())
                }

                is CreateTreatmentState.Error -> {
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = (state as CreateTreatmentState.Error).message,
                        color = MaterialTheme.colors.error
                    )
                }

                else -> Unit
            }
        }
    }
}