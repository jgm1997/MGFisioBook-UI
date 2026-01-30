package es.jgm1997.mgfisiobook.ui.components.common

import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.LocalNavigator
import androidx.compose.material.Icon
import compose.icons.FeatherIcons
import compose.icons.feathericons.ArrowLeft

@Composable
fun AppTopBar(title: String) {
    val navigator = LocalNavigator.current

    TopAppBar(title = { Text(title) }, navigationIcon = {
        IconButton(onClick = {navigator?.pop()}) {
            Icon(FeatherIcons.ArrowLeft, contentDescription = null)
        }
    })
}