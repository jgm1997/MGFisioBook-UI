package es.jgm1997.mgfisiobook.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import es.jgm1997.mgfisiobook.ui.components.appointments.AppointmentsList
import es.jgm1997.mgfisiobook.ui.components.common.ErrorComponent
import es.jgm1997.mgfisiobook.ui.components.common.LoadingComponent
import es.jgm1997.mgfisiobook.viewmodels.appointments.AppointmentsListState
import es.jgm1997.mgfisiobook.viewmodels.appointments.AppointmentsListViewModel
import kotlin.uuid.ExperimentalUuidApi

class AppointmentsListScreen : Screen {
    @OptIn(ExperimentalUuidApi::class)
    @Composable
    override fun Content() {
        val viewModel = getScreenModel<AppointmentsListViewModel>()
        val state by viewModel.state.collectAsState()
        val navigator = LocalNavigator.current

        LaunchedEffect(Unit) {
            viewModel.load()
        }

        when (state) {
            is AppointmentsListState.Loading -> LoadingComponent()
            is AppointmentsListState.Success ->
                AppointmentsList(
                    items = (state as AppointmentsListState.Success).appointments,
                    onSelect = { navigator?.push(AppointmentDetailScreen(it.id)) }
                )

            is AppointmentsListState.Error -> ErrorComponent((state as AppointmentsListState.Error).message)
            else -> Unit
        }
    }
}