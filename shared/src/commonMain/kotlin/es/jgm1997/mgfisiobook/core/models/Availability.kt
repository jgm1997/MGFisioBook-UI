package es.jgm1997.mgfisiobook.core.models

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class AvailabilitySlot(
    val start: LocalDateTime,
    val end: LocalDateTime,
    val available: Boolean
)

@OptIn(ExperimentalUuidApi::class)
@Serializable
data class AvailabilityPublic(
    val id: Uuid,
    @SerialName("therapist_id") val therapistId: Uuid,
    val weekday: Int,
    @SerialName("start_time") val startTime: LocalDateTime,
    @SerialName("end_time") val endTime: LocalDateTime
)

@Serializable
data class AvailabilityCreate(
    val weekday: Int,
    @SerialName("start_time") val startTime: LocalDateTime,
    @SerialName("end_time") val endTime: LocalDateTime
)
