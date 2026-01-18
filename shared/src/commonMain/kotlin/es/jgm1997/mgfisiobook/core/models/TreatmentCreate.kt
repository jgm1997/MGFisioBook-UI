package es.jgm1997.mgfisiobook.core.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TreatmentCreate(
    val name: String,
    val description: String,
    @SerialName("duration_minutes") val durationMinutes: Int,
    val price: Double
)
