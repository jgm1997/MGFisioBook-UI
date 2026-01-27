package es.jgm1997.mgfisiobook.core.models

import kotlinx.serialization.Serializable

@Serializable
data class DeviceTokenCreate(
    val token: String,
    val platform: String
)
