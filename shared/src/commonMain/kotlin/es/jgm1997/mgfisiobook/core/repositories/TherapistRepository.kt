package es.jgm1997.mgfisiobook.core.repositories

import es.jgm1997.mgfisiobook.core.auth.AuthStorage
import es.jgm1997.mgfisiobook.core.models.Therapist
import es.jgm1997.mgfisiobook.core.network.ApiConfig
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class TherapistRepository(private val client: HttpClient) {
    private val baseUrl = "${ApiConfig.baseUrl}/therapists"

    suspend fun getTherapist(id: Uuid): Therapist {
        return client.get("$baseUrl/$id") {
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer ${AuthStorage.loadToken()}")
        }.body()
    }
}