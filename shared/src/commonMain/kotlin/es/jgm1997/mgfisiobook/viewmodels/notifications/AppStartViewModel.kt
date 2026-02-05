package es.jgm1997.mgfisiobook.viewmodels.notifications

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import es.jgm1997.mgfisiobook.core.repositories.AppStartRepository
import es.jgm1997.mgfisiobook.shared.notifications.NotificationBridge
import es.jgm1997.shared.platform
import es.jgm1997.mgfisiobook.ui.navigation.AppNavigator
import es.jgm1997.mgfisiobook.ui.navigation.NavigationEvent
import es.jgm1997.mgfisiobook.ui.navigation.NavigationEventBus
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class AppStartViewModel(private val repository: AppStartRepository) : ScreenModel {
    init {
        NotificationBridge.onTokenReceived = { token ->
            registerDeviceToken(token)
        }

        NotificationBridge.onNotificationReceived = { data ->
            handleNotificationData(data)
        }
    }

    private fun registerDeviceToken(token: String) {
        screenModelScope.launch {
            try {
                repository.registerDeviceToken(token, platform())
            } catch (_: Exception) {
                //  Handle error
            }
        }
    }

    private fun handleNotificationData(data: Map<String, String>) {
        val appointmentId = data["appointmentId"] ?: return
        screenModelScope.launch {
            NavigationEventBus.send(NavigationEvent.ToAppointmentDetail(Uuid.parse(appointmentId)))
        }
    }
}