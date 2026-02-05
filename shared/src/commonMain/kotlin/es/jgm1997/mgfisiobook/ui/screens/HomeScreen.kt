package es.jgm1997.mgfisiobook.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import compose.icons.FeatherIcons
import compose.icons.feathericons.Activity
import compose.icons.feathericons.Calendar
import compose.icons.feathericons.Clock
import compose.icons.feathericons.Plus
import compose.icons.feathericons.User
import es.jgm1997.mgfisiobook.core.auth.hasRole
import es.jgm1997.mgfisiobook.core.auth.isAuthenticated
import kotlin.uuid.ExperimentalUuidApi

class HomeScreen : Screen {
    @OptIn(ExperimentalUuidApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        if (!isAuthenticated()) {
            LaunchedEffect(Unit) {
                navigator?.replaceAll(LoginScreen())
            }
            return
        }

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Bienvenido a MGFisioBook")

            Spacer(Modifier.height(16.dp))

            if (hasRole("patient")) {
                HomeCard(
                    "Mis citas",
                    FeatherIcons.Clock
                ) { navigator?.push(AppointmentsListScreen()) }
            } else {
                HomeCard("Mi disponibilidad", FeatherIcons.Calendar) {
                    navigator?.push(
                        MyAvailabilityScreen()
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            HomeCard(
                "Ver tratamientos",
                FeatherIcons.Activity
            ) { navigator?.push(TreatmentsScreen()) }
            if (hasRole("admin", "therapist")) {
                Spacer(Modifier.height(8.dp))
                HomeCard("Crear tratamiento", FeatherIcons.Plus) {
                    navigator?.push(
                        CreateTreatmentScreen()
                    )
                }
            }
            Spacer(Modifier.height(16.dp))
            HomeCard("Mi perfil", FeatherIcons.User) { navigator?.push(ProfileScreen()) }
        }
    }
}

@Composable
private fun HomeCard(title: String, icon: ImageVector, onClick: () -> Unit) {
    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth().clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        ) { onClick() }) {
        Row(Modifier.fillMaxWidth().padding(20.dp)) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colors.primary)
            Spacer(Modifier.width(16.dp))
            Text(title, style = MaterialTheme.typography.h6)
        }
    }
}