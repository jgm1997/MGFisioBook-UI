package es.jgm1997.mgfisiobook.core.repositories

import es.jgm1997.mgfisiobook.core.auth.AuthStorage
import es.jgm1997.mgfisiobook.core.models.Appointment
import es.jgm1997.mgfisiobook.core.models.AppointmentCreate
import es.jgm1997.mgfisiobook.core.network.ApiConfig
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class AppointmentsRepository(private val client: HttpClient) {
    private val baseUrl = "${ApiConfig.baseUrl}/appointments"

    suspend fun getAppointment(id: Uuid): Appointment {
        try {
            return client.get("$baseUrl/$id").body()
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

    suspend fun getAppointments(): List<Appointment> {
        try {
            return client.get(baseUrl) {
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

    suspend fun createAppointment(data: AppointmentCreate): Appointment {
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
}