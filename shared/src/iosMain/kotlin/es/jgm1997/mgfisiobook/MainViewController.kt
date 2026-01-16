package es.jgm1997.mgfisiobook

import androidx.compose.ui.window.ComposeUIViewController
import es.jgm1997.mgfisiobook.core.di.initKoin

fun MainViewController() = ComposeUIViewController {
    initKoin()
    App()
}