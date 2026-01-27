package es.jgm1997.mgfisiobook.shared.notifications

object NotificationBridge {
    var onTokenReceived: ((String) -> Unit)? = null
    var onNotificationReceived: ((Map<String, String>) -> Unit)? = null

    fun onNewToken(token: String) {
        onTokenReceived?.invoke(token)
    }

    fun onMessageReceived(data: Map<String, String>) {
        onNotificationReceived?.invoke(data)
    }
}