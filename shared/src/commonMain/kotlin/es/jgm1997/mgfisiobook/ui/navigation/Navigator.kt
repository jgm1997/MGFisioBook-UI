package es.jgm1997.mgfisiobook.ui.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import es.jgm1997.mgfisiobook.features.login.LoginScreen

@Composable
fun AppNavigator() {
    Navigator(LoginScreen())
}