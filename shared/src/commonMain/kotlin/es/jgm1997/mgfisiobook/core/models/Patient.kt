package es.jgm1997.mgfisiobook.core.models

import kotlinx.serialization.SerialName
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class Patient(
    val id: Uuid,
    val email: String,
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String,
    val notes: String,
    @SerialName("supabase_user_id") val supabaseUserId: Uuid
)
