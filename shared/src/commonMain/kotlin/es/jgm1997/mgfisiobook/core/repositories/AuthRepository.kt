package es.jgm1997.mgfisiobook.core.repositories

import es.jgm1997.mgfisiobook.core.models.AuthToken
import es.jgm1997.mgfisiobook.core.network.ApiConfig
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class AuthRepository(private val client: HttpClient) {
    @Serializable
    data class LoginRequest(val email: String, val password: String)

    @Serializable
    data class RegisterRequest(
        val email: String,
        val password: String,
        @SerialName("first_name") val firstName: String,
        @SerialName("last_name") val lastName: String
    )

    suspend fun login(email: String, password: String): AuthToken {
        try {
            val response = client.post("${ApiConfig.baseUrl}/auth/login") {
                contentType(ContentType.Application.Json)
                setBody(LoginRequest(email, password))
            }.body<AuthToken>()
            return response
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

    suspend fun register(
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ): AuthToken {
        try {
            return client.post("${ApiConfig.baseUrl}.auth/signup") {
                contentType(ContentType.Application.Json)
                setBody(RegisterRequest(email, password, firstName, lastName))
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