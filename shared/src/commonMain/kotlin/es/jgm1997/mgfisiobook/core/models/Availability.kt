package es.jgm1997.mgfisiobook.core.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Serializable
data class AvailabilityPublic(
    val id: Uuid,
    @SerialName("therapist_id") val therapistId: Uuid,
    val weekday: Int,
    @SerialName("start_time") val startTime: Instant,
    @SerialName("end_time") val endTime: Instant
)

@Serializable
data class AvailabilityCreate(
    val weekday: Int,
    @SerialName("start_time") val startTime: Instant,
    @SerialName("end_time") val endTime: Instant
)

@Serializable
data class AvailabilitySlot(
    val start: Instant,
    val end: Instant,
    val available: Boolean
)
