package es.jgm1997.mgfisiobook.core.network

import es.jgm1997.mgfisiobook.core.auth.AuthState
import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer

actual class HttpClientFactory {
    actual fun create(): HttpClient = HttpClient(Darwin) {
        install(Logging)
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
        install(Auth) {
            bearer {
                loadTokens {
                    AuthState.token.value?.let {
                        BearerTokens(it.accessToken, "")
                    }
                }
            }
        }
    }
}