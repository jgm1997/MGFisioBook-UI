package es.jgm1997.mgfisiobook.core.auth

import es.jgm1997.mgfisiobook.core.models.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.Serializable

class AuthRepository(private val client: HttpClient) {
    @Serializable
    data class LoginRequest(val email: String, val password: String)

    suspend fun login(email: String, password: String): User {
        return client.post("https://mgfisiobook.onrender.com/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(LoginRequest(email, password))
        }.body<User>()
    }
}