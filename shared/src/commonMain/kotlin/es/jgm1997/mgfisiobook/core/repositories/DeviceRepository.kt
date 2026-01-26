package es.jgm1997.mgfisiobook.core.repositories

import es.jgm1997.mgfisiobook.core.auth.AuthStorage
import es.jgm1997.mgfisiobook.core.network.ApiConfig
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class DeviceRepository(private val client: HttpClient) {
    suspend fun registerDevice(
        token: String,
        platform: String,
        appVersion: String?,
        userId: String
    ) {
        client.post("${ApiConfig.baseUrl}/devices") {
            header("Authorization", "Bearer ${AuthStorage.loadToken()}")
            setBody(
                mapOf(
                    "token" to token,
                    "platform" to platform,
                    "app_version" to appVersion,
                    "user_id" to userId
                )
            )
        }
    }
}