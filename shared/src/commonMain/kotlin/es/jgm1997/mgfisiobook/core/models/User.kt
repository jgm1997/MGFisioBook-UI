package es.jgm1997.mgfisiobook.core.models

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class User(
    val id: Uuid,
    val email: String,
    val name: String,
    val token: String
)
