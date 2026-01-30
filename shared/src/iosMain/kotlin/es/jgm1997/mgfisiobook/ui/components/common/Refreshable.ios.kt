package es.jgm1997.mgfisiobook.ui.components.common

import androidx.compose.runtime.Composable


@Composable
actual fun Refreshable(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    content: @Composable (() -> Unit)
) {
    content()
}
