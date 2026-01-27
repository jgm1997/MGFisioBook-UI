package es.jgm1997.mgfisiobook.core.models

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Serializable
data class Appointment(
    val id: Uuid,
    @SerialName("patient_id") val patientID: Uuid,
    @SerialName("therapist_id") val therapistId: Uuid,
    @SerialName("treatment_id") val treatmentId: Uuid,
    @SerialName("start_time") val startTime: LocalDateTime,
    @SerialName("end_time") val endTime: LocalDateTime,
    val status: String,
    val notes: String? = null
)

@OptIn(ExperimentalUuidApi::class)
@Serializable
data class AppointmentCreate(
    @SerialName("therapist_id") val therapistId: Uuid,
    @SerialName("patient_id") val patientID: Uuid,
    @SerialName("treatment_id") val treatmentId: Uuid,
    @SerialName("start_time") val startTime: LocalDateTime,
    @SerialName("end_time") val endTime: LocalDateTime,
)
