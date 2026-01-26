package es.jgm1997.mgfisiobook.core.di

import es.jgm1997.mgfisiobook.core.repositories.AppointmentsRepository
import es.jgm1997.mgfisiobook.core.repositories.AuthRepository
import es.jgm1997.mgfisiobook.core.auth.AuthViewModel
import es.jgm1997.mgfisiobook.core.repositories.DeviceRepository
import es.jgm1997.mgfisiobook.core.network.HttpClientFactory
import es.jgm1997.mgfisiobook.core.repositories.AvailabilityRepository
import es.jgm1997.mgfisiobook.core.repositories.TreatmentRepository
import es.jgm1997.mgfisiobook.viewmodels.appointments.AppointmentDetailViewModel
import es.jgm1997.mgfisiobook.viewmodels.appointments.AppointmentsListViewModel
import es.jgm1997.mgfisiobook.viewmodels.auth.LoginViewModel
import es.jgm1997.mgfisiobook.viewmodels.auth.RegisterViewModel
import es.jgm1997.mgfisiobook.viewmodels.availability.AvailabilityEditorViewModel
import es.jgm1997.mgfisiobook.viewmodels.treatments.CreateTreatmentViewModel
import es.jgm1997.mgfisiobook.viewmodels.availability.MyAvailabilityViewModel
import es.jgm1997.mgfisiobook.viewmodels.treatments.TreatmentsViewModel
import es.jgm1997.shared.AuthController
import es.jgm1997.shared.usecase.RegisterDeviceUseCase
import org.koin.dsl.module

val appModule = module {
    single { HttpClientFactory().create() }
    single { AuthRepository(get()) }
    single { TreatmentRepository(get()) }
    single { DeviceRepository(get()) }
    single { AppointmentsRepository(get()) }
    single { AvailabilityRepository(get()) }

    single { RegisterDeviceUseCase(get()) }

    single { AuthController(get(), get(), get()) }

    factory { AuthViewModel() }
    factory { LoginViewModel(get()) }
    factory { RegisterViewModel(get()) }
    factory { TreatmentsViewModel(get()) }
    factory { CreateTreatmentViewModel(get()) }
    factory { AppointmentDetailViewModel(get()) }
    factory { AppointmentsListViewModel(get()) }
    factory { MyAvailabilityViewModel(get()) }
    factory { AvailabilityEditorViewModel(get()) }
}