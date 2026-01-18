package es.jgm1997.mgfisiobook.core.treatments

import es.jgm1997.mgfisiobook.core.models.Treatment
import es.jgm1997.mgfisiobook.core.models.TreatmentCreate
import es.jgm1997.mgfisiobook.core.network.ApiConfig
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class TreatmentRepository(private val client: HttpClient) {
    suspend fun getTreatments(): List<Treatment> {
        try {
            return client.get("${ApiConfig.baseUrl}/treatments").body()
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

    suspend fun createTreatment(treatmentCreate: TreatmentCreate) {
        try {
            client.post("${ApiConfig.baseUrl}/treatments") {
                contentType(ContentType.Application.Json)
                setBody(treatmentCreate)
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