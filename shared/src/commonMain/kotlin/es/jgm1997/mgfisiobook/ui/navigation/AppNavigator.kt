package es.jgm1997.mgfisiobook.ui.navigation

import cafe.adriel.voyager.navigator.Navigator
import es.jgm1997.mgfisiobook.ui.screens.AppointmentDetailScreen
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

object AppNavigator {
    var navigator: Navigator? = null

    @OptIn(ExperimentalUuidApi::class)
    fun navigateToAppointmentDetail(appointmentId: Uuid) {
        navigator?.push(AppointmentDetailScreen(appointmentId))
    }
}