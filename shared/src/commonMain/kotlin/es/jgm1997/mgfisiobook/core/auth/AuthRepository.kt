package es.jgm1997.mgfisiobook.core.auth

import es.jgm1997.mgfisiobook.core.models.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.Serializable

private const val BASE_URL = "https://mgfisiobook.onrender.com/"

class AuthRepository(private val client: HttpClient) {
    @Serializable
    data class LoginRequest(val email: String, val password: String)

    @Serializable
    data class RegisterRequest(val email: String, val password: String, val name: String)

    suspend fun login(email: String, password: String): User {
        return client.post("${BASE_URL}auth/login") {
            contentType(ContentType.Application.Json)
            setBody(LoginRequest(email, password))
        }.body<User>()
    }

    suspend fun register(email: String, password: String, name: String): User {
        return client.post("${BASE_URL}auth/signup") {
            contentType(ContentType.Application.Json)
            setBody(RegisterRequest(email, password, name))
        }.body()
    }
}