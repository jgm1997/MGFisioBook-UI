package es.jgm1997.mgfisiobook.android

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import es.jgm1997.mgfisiobook.shared.notifications.NotificationBridge

class FcmService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        NotificationBridge.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        NotificationBridge.onMessageReceived(message.data)

    }
}