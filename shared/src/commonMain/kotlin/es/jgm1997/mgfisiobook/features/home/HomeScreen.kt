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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.appstractive.jwt.JWT
import com.appstractive.jwt.from
import es.jgm1997.mgfisiobook.core.auth.AuthState
import es.jgm1997.mgfisiobook.features.treatments.CreateTreatmentScreen
import es.jgm1997.mgfisiobook.features.treatments.TreatmentsScreen
import kotlinx.serialization.json.jsonObject

class HomeScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val token by AuthState.token.collectAsState()
        val jwtMetadata = JWT.from(token!!.accessToken).claims["user_metadata"]
        println("JWT: $jwtMetadata")

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
            if (jwtMetadata!!.jsonObject["role"].toString() != "patient") {
                Button(onClick = { navigator?.push(CreateTreatmentScreen()) }) {
                    Text("Crear tratamiento")
                }
            }
        }

    }
}