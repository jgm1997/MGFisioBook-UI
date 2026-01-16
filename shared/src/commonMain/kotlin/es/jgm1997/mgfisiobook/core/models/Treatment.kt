package es.jgm1997.mgfisiobook.core.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Serializable
data class Treatment(
    val id: Uuid,
    val name: String,
    val description: String,
    @SerialName("duration_minutes") val durationMinutes: Int,
    val price: Double
)
