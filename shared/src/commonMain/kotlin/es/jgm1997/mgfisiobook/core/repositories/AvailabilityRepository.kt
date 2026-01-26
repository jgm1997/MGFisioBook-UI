package es.jgm1997.mgfisiobook.core.repositories

import es.jgm1997.mgfisiobook.core.auth.AuthStorage
import es.jgm1997.mgfisiobook.core.models.AvailabilityCreate
import es.jgm1997.mgfisiobook.core.models.AvailabilityPublic
import es.jgm1997.mgfisiobook.core.network.ApiConfig
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class AvailabilityRepository(private val client: HttpClient) {
    private val baseUrl = "${ApiConfig.baseUrl}/availability"

    suspend fun getMyAvailability(): List<AvailabilityPublic> {
        try {
            return client.get("$baseUrl/me") {
                contentType(ContentType.Application.Json)
                header("Authorization", "Bearer ${AuthStorage.loadToken()}")
            }.body()
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

    suspend fun createAvailability(data: AvailabilityCreate): AvailabilityPublic {
        try {
            return client.post(baseUrl) {
                contentType(ContentType.Application.Json)
                header("Authorization", "Bearer ${AuthStorage.loadToken()}")
                setBody(data)
            }.body()
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

    suspend fun deleteAvailabilitySlot(id: Uuid) {
        try {
            client.delete("$baseUrl/$id") {
                contentType(ContentType.Application.Json)
                header("Authorization", "Bearer ${AuthStorage.loadToken()}")
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