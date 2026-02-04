package es.jgm1997.mgfisiobook.core.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Serializable
data class Appointment(
    val id: Uuid,
    @SerialName("patient_id") val patientId: Uuid,
    @SerialName("therapist_id") val therapistId: Uuid,
    @SerialName("treatment_id") val treatmentId: Uuid,
    @SerialName("start_time") val startTime: Instant,
    @SerialName("end_time") val endTime: Instant,
    val status: String,
    val notes: String? = null
)

@OptIn(ExperimentalUuidApi::class)
@Serializable
data class AppointmentCreate(
    @SerialName("therapist_id") val therapistId: Uuid,
    @SerialName("patient_id") val patientID: Uuid,
    @SerialName("treatment_id") val treatmentId: Uuid,
    @SerialName("start_time") val startTime: Instant,
    @SerialName("end_time") val endTime: Instant,
)

@OptIn(ExperimentalUuidApi::class)
@Serializable
data class AppointmentPublic(
    val id: Uuid,
    val patient: Patient,
    val therapist: Therapist,
    val treatment: Treatment,
    @SerialName("start_time") val startTime: Instant,
    @SerialName("end_time") val endTime: Instant,
    val status: String,
    val notes: String? = null
)
