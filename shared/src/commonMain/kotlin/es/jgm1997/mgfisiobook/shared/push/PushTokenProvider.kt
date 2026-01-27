package es.jgm1997.mgfisiobook.shared.push

interface PushTokenProvider {
    suspend fun getPushToken(): String?
    fun platform(): String
    fun appVersion(): String?
}