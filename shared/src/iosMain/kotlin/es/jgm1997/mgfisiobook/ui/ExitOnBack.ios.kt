package es.jgm1997.mgfisiobook.ui

import androidx.compose.runtime.Composable

// No-op on iOS / other native targets
@Composable
actual fun ExitAppOnBack() {
    // No-op: finishing the Android activity is not applicable on iOS
}
