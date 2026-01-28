package es.jgm1997.mgfisiobook.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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
import cafe.adriel.voyager.navigator.LocalNavigator
import es.jgm1997.mgfisiobook.ui.components.common.ErrorComponent
import es.jgm1997.mgfisiobook.viewmodels.auth.UserProfileState
import es.jgm1997.mgfisiobook.viewmodels.auth.UserProfileViewModel

class ProfileScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel = getScreenModel<UserProfileViewModel>()
        val state by viewModel.state.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.load()
        }

        when (state) {
            is UserProfileState.Loading -> {
                Box(Modifier.fillMaxSize()) {
                    CircularProgressIndicator(Modifier.padding(32.dp))
                }
            }

            is UserProfileState.Error ->
                ErrorComponent((state as UserProfileState.Error).message)

            is UserProfileState.Success -> {
                val profile = (state as UserProfileState.Success).profile

                Column(Modifier.fillMaxSize().padding(16.dp)) {
                    Text("Mi perfil", style = MaterialTheme.typography.h5)
                    Spacer(Modifier.height(16.dp))

                    Text("Nombre: ${profile.firstName} ${profile.lastName}")
                    Text("Correo electrónico: ${profile.email}")
                    Text("Rol: ${profile.role}")

                    Spacer(Modifier.height(32.dp))
                    Button(
                        onClick = {
                            viewModel.logout {
                                navigator?.replaceAll(LoginScreen())
                            }
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error)
                    ) {
                        Text("Cerrar sesión", color = MaterialTheme.colors.onError)
                    }
                }
            }

            else -> Unit
        }
    }
}