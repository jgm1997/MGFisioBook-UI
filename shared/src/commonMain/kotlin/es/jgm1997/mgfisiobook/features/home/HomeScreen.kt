package es.jgm1997.mgfisiobook.features.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import es.jgm1997.mgfisiobook.core.auth.AuthViewModel
import es.jgm1997.mgfisiobook.core.auth.hasRole
import es.jgm1997.mgfisiobook.core.auth.isAuthenticated
import es.jgm1997.mgfisiobook.features.login.LoginScreen
import es.jgm1997.mgfisiobook.features.treatments.CreateTreatmentScreen
import es.jgm1997.mgfisiobook.features.treatments.TreatmentsScreen

class HomeScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val authViewModel = getScreenModel<AuthViewModel>()

        if (!isAuthenticated()) {
            navigator?.replace(LoginScreen())
            return
        }

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Bienvenido a MGFisioBook")

            Spacer(Modifier.height(16.dp))

            Button(onClick = { navigator?.push(TreatmentsScreen()) }) {
                Text("Ver tratamientos")
            }
            if (hasRole("admin", "therapist")) {
                Button(onClick = { navigator?.push(CreateTreatmentScreen()) }) {
                    Text("Crear tratamiento")
                }
            }
            Spacer(Modifier.height(16.dp))
            Button(onClick = {
                authViewModel.logout()
                navigator?.replace(LoginScreen())
            }) {
                Text("Cerrar sesión")
            }
        }
    }
}