package es.jgm1997.mgfisiobook.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import es.jgm1997.mgfisiobook.core.auth.AuthState
import es.jgm1997.mgfisiobook.core.auth.isAuthenticated
import es.jgm1997.mgfisiobook.core.auth.isExpired
import es.jgm1997.mgfisiobook.ui.screens.HomeScreen
import es.jgm1997.mgfisiobook.ui.screens.LoginScreen

object AppNavigatorRef {
    var navigator: Navigator? = null
}

@Composable
fun AppNavigator() {
    val token by AuthState.token.collectAsState()
    Navigator(if (!isAuthenticated() || isExpired()) LoginScreen() else HomeScreen()) { navigator ->
        // Guardar la referencia al navigator y limpiarla cuando se dispose
        DisposableEffect(navigator) {
            AppNavigatorRef.navigator = navigator
            onDispose { AppNavigatorRef.navigator = null }
        }
        SlideTransition(navigator)
    }
}
