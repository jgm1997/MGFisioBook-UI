package es.jgm1997.mgfisiobook.features.login

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.koin.getScreenModel
import es.jgm1997.mgfisiobook.features.home.HomeScreen

class LoginScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel = getScreenModel<LoginViewModel>()
        val state by viewModel.state.collectAsState()

        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        Column(modifier = Modifier.padding(16.dp)) {
            TextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
            Spacer(Modifier.height(12.dp))
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") })
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = { viewModel.login(email, password) },
                enabled = state !is LoginState.Loading
            ) {
                Text("Login")
            }

            when (state) {
                is LoginState.Loading -> {
                    Spacer(Modifier.height(16.dp))
                    CircularProgressIndicator()
                }

                is LoginState.Error -> {
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = (state as LoginState.Error).message,
                        color = MaterialTheme.colors.error
                    )
                }

                is LoginState.Success -> navigator?.push(HomeScreen())

                else -> Unit
            }
        }
    }
}