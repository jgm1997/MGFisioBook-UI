package es.jgm1997.mgfisiobook.ui

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun ExitAppOnBack() {
    val context = LocalContext.current
    val activity = remember(context) { context as? Activity }
    val dispatcher = (activity as? ComponentActivity)?.onBackPressedDispatcher

    DisposableEffect(dispatcher) {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.finish()
            }
        }
        dispatcher?.addCallback(callback)
        onDispose { callback.remove() }
    }
}
