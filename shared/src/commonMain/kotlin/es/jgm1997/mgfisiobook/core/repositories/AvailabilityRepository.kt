package es.jgm1997.mgfisiobook.core.repositories

import es.jgm1997.mgfisiobook.core.models.AvailabilityCreate
import es.jgm1997.mgfisiobook.core.models.AvailabilityPublic
import es.jgm1997.mgfisiobook.core.models.AvailabilitySlot
import es.jgm1997.mgfisiobook.core.network.ApiConfig
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.datetime.LocalDate
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class AvailabilityRepository(private val client: HttpClient) {
    private val baseUrl = "${ApiConfig.baseUrl}/availability"

    suspend fun getDailyAvailability(date: LocalDate, therapistId: Uuid): List<AvailabilitySlot> {
        return client.get(baseUrl) {
            parameter("date", date)
            parameter("therapistId", therapistId)
        }.body()
    }

    suspend fun getMyAvailability(): List<AvailabilityPublic> {
        return client.get("$baseUrl/me").body()
    }

    suspend fun getTherapistAvailability(therapistId: Uuid): List<AvailabilityPublic> {
        return client.get("$baseUrl/$therapistId").body()
    }

    suspend fun createAvailability(data: AvailabilityCreate): AvailabilityPublic {
        return client.post(baseUrl) { setBody(data) }.body()
    }
}