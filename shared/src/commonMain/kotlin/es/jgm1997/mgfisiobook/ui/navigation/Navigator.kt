package es.jgm1997.mgfisiobook.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import es.jgm1997.mgfisiobook.core.auth.AuthState
import es.jgm1997.mgfisiobook.core.auth.isExpired
import es.jgm1997.mgfisiobook.ui.screens.HomeScreen
import es.jgm1997.mgfisiobook.ui.screens.LoginScreen

@Composable
fun AppNavigator() {
    val token by AuthState.token.collectAsState()
    println(token)
    Navigator(if (token == null || isExpired()) LoginScreen() else HomeScreen()) { navigator ->
        SlideTransition(navigator)
    }
}

