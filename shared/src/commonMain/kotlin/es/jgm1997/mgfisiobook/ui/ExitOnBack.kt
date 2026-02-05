package es.jgm1997.mgfisiobook.ui

import androidx.compose.runtime.Composable

/**
 * Expect composable that, on Android, will finish the Activity when the system Back is pressed.
 * On other platforms it's a no-op.
 */
@Composable
expect fun ExitAppOnBack()
