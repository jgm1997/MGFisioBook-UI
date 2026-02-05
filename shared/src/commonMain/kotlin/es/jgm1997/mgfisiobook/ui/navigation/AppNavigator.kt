package es.jgm1997.mgfisiobook.ui.navigation

import cafe.adriel.voyager.navigator.Navigator
import es.jgm1997.mgfisiobook.ui.screens.AppointmentDetailScreen
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

object AppNavigator {
    // No almacenar una referencia separada; usar la referencia administrada por AppNavigatorRef

    @OptIn(ExperimentalUuidApi::class)
    fun navigateToAppointmentDetail(appointmentId: Uuid) {
        try {
            AppNavigatorRef.navigator?.push(AppointmentDetailScreen(appointmentId))
        } catch (e: Exception) {
            // Loguear y ignorar si el navigator está en un estado inválido
            println("Navigation failed: ${e.message}")
        }
    }
}