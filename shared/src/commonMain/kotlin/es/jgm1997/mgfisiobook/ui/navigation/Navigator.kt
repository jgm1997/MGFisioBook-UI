package es.jgm1997.mgfisiobook.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.navigator.Navigator
import es.jgm1997.mgfisiobook.core.auth.AuthState
import es.jgm1997.mgfisiobook.features.home.HomeScreen
import es.jgm1997.mgfisiobook.features.login.LoginScreen

@Composable
fun AppNavigator() {
    val token by AuthState.token.collectAsState()

    if (token == null) {
        Navigator(LoginScreen())
    } else {
        Navigator(HomeScreen())
    }
}