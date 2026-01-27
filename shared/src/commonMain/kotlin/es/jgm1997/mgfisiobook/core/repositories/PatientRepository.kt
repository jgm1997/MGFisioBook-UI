package es.jgm1997.mgfisiobook.core.repositories

import es.jgm1997.mgfisiobook.core.auth.AuthStorage
import es.jgm1997.mgfisiobook.core.models.Patient
import es.jgm1997.mgfisiobook.core.network.ApiConfig
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType

class PatientRepository(private val client: HttpClient) {
    private val baseUrl = "${ApiConfig.baseUrl}/patients"

    suspend fun getAllPatients(): List<Patient> {
        return client.get(baseUrl) {
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer ${AuthStorage.loadToken()}")
        }.body()
    }
}