package es.jgm1997.mgfisiobook

import androidx.compose.ui.window.ComposeUIViewController
import es.jgm1997.mgfisiobook.core.di.initKoin
import es.jgm1997.mgfisiobook.di.iosModule

fun MainViewController() = ComposeUIViewController {
    initKoin(iosModule)
    App()
}