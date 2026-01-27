package es.jgm1997.mgfisiobook.core.repositories

import es.jgm1997.mgfisiobook.core.auth.AuthStorage
import es.jgm1997.mgfisiobook.core.models.DeviceTokenCreate
import es.jgm1997.mgfisiobook.core.network.ApiConfig
import io.ktor.client.HttpClient
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class AppStartRepository(private val client: HttpClient) {
    private val baseUrl = "${ApiConfig.baseUrl}/devices"
    suspend fun registerDeviceToken(token: String, platform: String) {
        try {
            client.post(baseUrl) {
                header("Authorization", "Bearer ${AuthStorage.loadToken()}")
                setBody(DeviceTokenCreate(token, platform))
            }
        } catch (e: ClientRequestException) {
            println("Credenciales incorrectas: ${e.response.status.description}")
            throw Exception("Credenciales incorrectas: ${e.response.status.description}")
        } catch (e: ServerResponseException) {
            println("Error del servidor: ${e.response.status.description}")
            throw Exception("Error del servidor: ${e.response.status.description}")
        } catch (e: Exception) {
            println("Error de conexión: ${e.message}")
            throw Exception("Error de conexión: ${e.message}")
        }
    }
}