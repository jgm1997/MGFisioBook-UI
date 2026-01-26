package es.jgm1997.mgfisiobook.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import es.jgm1997.mgfisiobook.viewmodels.auth.RegisterState
import es.jgm1997.mgfisiobook.viewmodels.auth.RegisterViewModel

class RegisterScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel = getScreenModel<RegisterViewModel>()
        val state by viewModel.state.collectAsState()

        var firstName by remember { mutableStateOf("") }
        var lastName by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Registro", style = MaterialTheme.typography.h5)
            Spacer(Modifier.height(16.dp))
            TextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text("Nombre") }
            )
            Spacer(Modifier.height(12.dp))
            TextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text("Apellidos") }
            )
            Spacer(Modifier.height(12.dp))
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            Spacer(Modifier.height(12.dp))
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = { viewModel.register(firstName, lastName, email, password) },
                enabled = state !is RegisterState.Loading
            ) {
                Text("Crear cuenta")
            }
            Spacer(Modifier.height(16.dp))
            // El flow es Login -> Register. Por tanto, para ir al login, solo hay que volver atrás
            TextButton(onClick = { navigator?.pop() }) {
                Text("¿Ya tienes una cuenta? Inicia sesión")
            }

            when (state) {
                is RegisterState.Loading -> {
                    Spacer(Modifier.height(16.dp))
                    CircularProgressIndicator()
                }

                is RegisterState.Error -> {
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = (state as RegisterState.Error).message,
                        color = MaterialTheme.colors.error
                    )
                }

                is RegisterState.Success -> navigator?.push(HomeScreen())
                else -> Unit
            }
        }
    }
}