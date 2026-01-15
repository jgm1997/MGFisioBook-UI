package es.jgm1997.mgfisiobook.core.network;

import io.ktor.client.HttpClient;
import io.ktor.client.engine.okhttp.OkHttp;
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

actual class HttpClientFactory {
    actual fun create(): HttpClient = HttpClient(OkHttp) {
        install(Logging)
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }
}
