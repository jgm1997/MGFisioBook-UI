package es.jgm1997.mgfisiobook.features.treatments

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import es.jgm1997.mgfisiobook.core.auth.isAuthenticated
import es.jgm1997.mgfisiobook.features.login.LoginScreen

class TreatmentsScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel = getScreenModel<TreatmentsViewModel>()
        val state by viewModel.state.collectAsState()

        if (!isAuthenticated()) {
            navigator?.replace(LoginScreen())
            return
        }

        when (state) {
            is TreatmentsState.Loading -> {
                Box(Modifier.fillMaxSize()) {
                    CircularProgressIndicator(Modifier.padding(32.dp))
                }
            }

            is TreatmentsState.Success -> {
                val treatments = (state as TreatmentsState.Success).treatments

                LazyColumn(modifier = Modifier.padding(16.dp)) {
                    items(treatments) { treatment ->
                        Card(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            elevation = 4.dp
                        ) {
                            Column(Modifier.padding(16.dp)) {
                                Text(treatment.name, style = MaterialTheme.typography.h6)
                                Spacer(Modifier.height(4.dp))
                                Text(treatment.description)
                                Spacer(Modifier.height(8.dp))
                                Text("Duración: ${treatment.durationMinutes} minutos")
                                Text("Precio: ${treatment.price}€")
                            }
                        }
                    }
                }
            }

            is TreatmentsState.Error -> {
                Text(
                    text = (state as TreatmentsState.Error).message,
                    color = MaterialTheme.colors.error
                )
            }
        }
    }
}