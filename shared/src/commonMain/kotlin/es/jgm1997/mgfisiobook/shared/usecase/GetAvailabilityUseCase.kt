package es.jgm1997.mgfisiobook.shared.usecase

import es.jgm1997.mgfisiobook.core.repositories.AppointmentsRepository
import es.jgm1997.mgfisiobook.core.repositories.AvailabilityRepository
import kotlinx.datetime.LocalDate
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class GetAvailabilityUseCase(private val repository: AvailabilityRepository) {
    suspend operator fun invoke(date: LocalDate, therapistId: Uuid) {
        repository.getDailyAvailability(date, therapistId)
    }
}