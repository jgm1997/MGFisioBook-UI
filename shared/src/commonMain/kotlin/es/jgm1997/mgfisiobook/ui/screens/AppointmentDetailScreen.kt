package es.jgm1997.mgfisiobook.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import es.jgm1997.mgfisiobook.ui.components.appointments.AppointmentDetailContent
import es.jgm1997.mgfisiobook.ui.components.common.ErrorComponent
import es.jgm1997.mgfisiobook.viewmodels.appointments.AppointmentDetailState
import es.jgm1997.mgfisiobook.viewmodels.appointments.AppointmentDetailViewModel
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class AppointmentDetailScreen(val appointmentId: Uuid) : Screen {
    @Composable
    override fun Content() {
        val viewModel = getScreenModel<AppointmentDetailViewModel>()
        val state by viewModel.state.collectAsState()

        LaunchedEffect(appointmentId) {
            viewModel.load(appointmentId)
        }

        when (state) {
            is AppointmentDetailState.Loading -> {
                Box(Modifier.fillMaxSize()) {
                    CircularProgressIndicator(Modifier.padding(32.dp))
                }
            }

            is AppointmentDetailState.Success ->
                AppointmentDetailContent((state as AppointmentDetailState.Success).appointment)


            is AppointmentDetailState.Error ->
                ErrorComponent((state as AppointmentDetailState.Error).message)

            else -> Unit
        }
    }
}