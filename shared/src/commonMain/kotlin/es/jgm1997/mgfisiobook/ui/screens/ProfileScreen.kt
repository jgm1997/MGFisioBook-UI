package es.jgm1997.mgfisiobook.ui.screens

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
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
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
import cafe.adriel.voyager.navigator.currentOrThrow
import compose.icons.FeatherIcons
import compose.icons.feathericons.Mail
import compose.icons.feathericons.Shield
import compose.icons.feathericons.User
import es.jgm1997.mgfisiobook.ui.components.common.AppTopBar
import es.jgm1997.mgfisiobook.ui.components.common.ErrorComponent
import es.jgm1997.mgfisiobook.ui.components.common.LoadingComponent
import es.jgm1997.mgfisiobook.viewmodels.auth.UserProfileState
import es.jgm1997.mgfisiobook.viewmodels.auth.UserProfileViewModel

class ProfileScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getScreenModel<UserProfileViewModel>()
        val state by viewModel.state.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.load()
        }

        // Manejar navegación cuando el usuario cierra sesión
        // Evitamos el crash del lifecycle navegando solo cuando el estado cambia
        LaunchedEffect(state) {
            if (state is UserProfileState.LoggedOut) {
                navigator.popAll()
                navigator.push(LoginScreen())
            }
        }

        // No mostrar nada si ya navegamos
        if (state is UserProfileState.LoggedOut) {
            return
        }

        Column(
            Modifier.fillMaxSize().padding(vertical = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            AppTopBar("Mi perfil")


            when (state) {
                is UserProfileState.Loading -> LoadingComponent(32.dp)

                is UserProfileState.Error ->
                    ErrorComponent((state as UserProfileState.Error).message)

                is UserProfileState.Success -> {
                    val profile = (state as UserProfileState.Success).profile

                    Column(Modifier.fillMaxWidth().padding(16.dp)) {
                        Card(
                            elevation = 4.dp,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(Modifier.fillMaxWidth().padding(20.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        FeatherIcons.User,
                                        contentDescription = null,
                                        tint = MaterialTheme.colors.primary
                                    )
                                    Spacer(Modifier.width(12.dp))
                                    Text(
                                        "${profile.firstName} ${profile.lastName}",
                                        style = MaterialTheme.typography.h6
                                    )
                                }
                                Spacer(Modifier.height(12.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        FeatherIcons.Mail,
                                        contentDescription = null,
                                        tint = MaterialTheme.colors.primary
                                    )
                                    Spacer(Modifier.width(12.dp))
                                    Text(
                                        profile.email,
                                        style = MaterialTheme.typography.body1
                                    )
                                }
                                Spacer(Modifier.height(12.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        FeatherIcons.Shield,
                                        contentDescription = null,
                                        tint = MaterialTheme.colors.primary
                                    )
                                    Spacer(Modifier.width(12.dp))
                                    Text(
                                        "Rol: ${profile.role}",
                                        style = MaterialTheme.typography.body1
                                    )
                                }
                            }
                        }
                    }
                    Spacer(Modifier.height(32.dp))
                    Button(
                        onClick = { viewModel.logout() },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.error
                        )
                    ) {
                        Text("Cerrar sesión", color = MaterialTheme.colors.onError)
                    }
                }

                else -> Unit
            }
        }
    }
}