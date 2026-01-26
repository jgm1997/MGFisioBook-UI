package es.jgm1997.shared.usecase

import es.jgm1997.mgfisiobook.core.repositories.AppointmentsRepository
import kotlinx.datetime.LocalDate
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class GetAvailabilityUseCase(private val repository: AppointmentsRepository) {
    suspend operator fun invoke(date: LocalDate, therapistId: Uuid) {
        repository.getAvailability(date, therapistId)
    }
}