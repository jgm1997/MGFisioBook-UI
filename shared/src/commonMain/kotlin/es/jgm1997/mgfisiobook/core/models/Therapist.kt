package es.jgm1997.mgfisiobook.core.models

import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Serializable
data class Therapist(
    val id: Uuid,
    val name: String,
    val specialty: String? = null,
    val phone: String? = null,
    val email: String? = null,
    val active: Boolean = true
)