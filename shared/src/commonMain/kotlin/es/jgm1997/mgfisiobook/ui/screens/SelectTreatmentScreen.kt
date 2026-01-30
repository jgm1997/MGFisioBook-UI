package es.jgm1997.mgfisiobook.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import es.jgm1997.mgfisiobook.ui.components.treatments.TreatmentList
import es.jgm1997.mgfisiobook.viewmodels.appointments.CreateAppointmentViewModel
import es.jgm1997.mgfisiobook.viewmodels.treatments.TreatmentsState
import es.jgm1997.mgfisiobook.viewmodels.treatments.TreatmentsViewModel
import kotlinx.datetime.LocalDateTime
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class SelectTreatmentScreen(
    private val therapistId: Uuid,
    private val startTime: LocalDateTime,
    private val endTime: LocalDateTime
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val treatmentsViewModel = getScreenModel<TreatmentsViewModel>()
        val appointmentViewModel = getScreenModel<CreateAppointmentViewModel>()
        val state by treatmentsViewModel.state.collectAsState()

        LaunchedEffect(Unit) {
            appointmentViewModel.setSlot(startTime, endTime, therapistId)
        }

        TreatmentList(
            treatments = (state as TreatmentsState.Success).treatments,
            onSelect = { treatment ->
                appointmentViewModel.setTreatment(treatment.id)
                navigator?.push(SelectPatientScreen())
            })
    }
}