package es.jgm1997.mgfisiobook

import androidx.compose.runtime.Composable
import es.jgm1997.mgfisiobook.ui.navigation.AppNavigator
import es.jgm1997.mgfisiobook.ui.theme.AppTheme

@Composable
fun App() {
    AppTheme {
        AppNavigator()
    }
}
