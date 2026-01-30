package es.jgm1997.mgfisiobook.ui.components.common

import androidx.compose.runtime.Composable


@Composable
expect fun Refreshable(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    content: @Composable () -> Unit
)