package es.jgm1997.mgfisiobook.core.repositories

import es.jgm1997.mgfisiobook.core.auth.AuthState
import es.jgm1997.mgfisiobook.core.auth.AuthStorage
import es.jgm1997.mgfisiobook.core.models.AuthToken
import es.jgm1997.mgfisiobook.core.models.LoginRequest
import es.jgm1997.mgfisiobook.core.models.RegisterRequest
import es.jgm1997.mgfisiobook.core.models.UserInfo
import es.jgm1997.mgfisiobook.core.network.ApiConfig
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType

class AuthRepository(private val client: HttpClient) {
    private val baseUrl = "${ApiConfig.baseUrl}/auth"


    suspend fun login(email: String, password: String): AuthToken {
        try {
            // Primero obtenemos la respuesta cruda para comprobar el status y evitar excepciones de transformación
            val response: HttpResponse = client.post("$baseUrl/login") {
                contentType(ContentType.Application.Json)
                setBody(LoginRequest(email, password))
            }

            val statusCode = response.status.value
            if (statusCode in 200..299) {
                val token = response.body<AuthToken>()
                if (token.accessToken == null) throw Exception("No token provided")
                return token
            } else {
                val text = response.bodyAsText()
                println("Error en login. Status: ${response.status}, ContentType: ${response.headers["Content-Type"]}")
                println("Body: $text")
                throw Exception(text.ifBlank { "Credenciales incorrectas: ${response.status.description}" })
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

    suspend fun register(
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ): AuthToken {
        try {
            val response: HttpResponse = client.post("$baseUrl/signup") {
                contentType(ContentType.Application.Json)
                setBody(RegisterRequest(email, password, firstName, lastName))
            }

            return if (response.status.value in 200..299) {
                response.body()
            } else {
                val text = response.bodyAsText()
                println("Error en register. Status: ${response.status}, Body: $text")
                throw Exception(text.ifBlank { "Error: ${response.status.description}" })
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

    suspend fun profile(): UserInfo {
        try {
            val response: HttpResponse = client.get("$baseUrl/me") {
                contentType(ContentType.Application.Json)
                header("Authorization", "Bearer ${AuthStorage.loadToken()}")
            }

            return if (response.status.value in 200..299) {
                response.body()
            } else {
                val text = response.bodyAsText()
                println("Error en profile. Status: ${response.status}, Body: $text")
                throw Exception(text.ifBlank { "Error: ${response.status.description}" })
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

    suspend fun logout() {
        try {
            val response: HttpResponse = client.post("$baseUrl/logout")

            if (response.status.value in 200..299) {
                AuthState.clear()
            } else {
                val text = response.bodyAsText()
                println("Error en logout. Status: ${response.status}, Body: $text")
                throw Exception(text.ifBlank { "Error: ${response.status.description}" })
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