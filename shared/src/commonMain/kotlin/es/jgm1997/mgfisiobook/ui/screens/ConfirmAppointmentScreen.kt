package es.jgm1997.mgfisiobook.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import es.jgm1997.mgfisiobook.ui.components.appointments.ConfirmAppointment
import es.jgm1997.mgfisiobook.ui.components.common.ErrorComponent
import es.jgm1997.mgfisiobook.ui.components.common.LoadingComponent
import es.jgm1997.mgfisiobook.viewmodels.appointments.CreateAppointmentState
import es.jgm1997.mgfisiobook.viewmodels.appointments.CreateAppointmentViewModel
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
class ConfirmAppointmentScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel = getScreenModel<CreateAppointmentViewModel>()
        val state by viewModel.state.collectAsState()

        when (state) {
            is CreateAppointmentState.Loading -> LoadingComponent()
            is CreateAppointmentState.Error ->
                ErrorComponent((state as CreateAppointmentState.Error).message)

            is CreateAppointmentState.Form -> {
                val form = state as CreateAppointmentState.Form
                ConfirmAppointment(
                    form = form,
                    patientName = form.patientId.toString(),
                    treatmentName = form.treatmentId.toString(),
                    onConfirm = {
                        viewModel.create { appointmentId ->
                            navigator?.replace(AppointmentDetailScreen(appointmentId))
                        }
                    }
                )
            }

            else -> Unit
        }
    }
}