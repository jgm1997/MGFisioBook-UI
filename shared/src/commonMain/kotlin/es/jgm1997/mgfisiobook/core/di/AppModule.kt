package es.jgm1997.mgfisiobook.core.di

import es.jgm1997.mgfisiobook.core.auth.AuthRepository
import es.jgm1997.mgfisiobook.core.auth.AuthViewModel
import es.jgm1997.mgfisiobook.core.device.DeviceRepository
import es.jgm1997.mgfisiobook.core.network.HttpClientFactory
import es.jgm1997.mgfisiobook.core.treatments.TreatmentRepository
import es.jgm1997.mgfisiobook.features.login.LoginViewModel
import es.jgm1997.mgfisiobook.features.register.RegisterViewModel
import es.jgm1997.mgfisiobook.features.treatments.CreateTreatmentViewModel
import es.jgm1997.mgfisiobook.features.treatments.TreatmentsViewModel
import es.jgm1997.shared.AuthController
import es.jgm1997.shared.push.RegisterDeviceUseCase
import org.koin.dsl.module

val appModule = module {
    single { HttpClientFactory().create() }
    single { AuthRepository(get()) }
    single { TreatmentRepository(get()) }
    single { DeviceRepository(get()) }

    single { RegisterDeviceUseCase(get()) }

    single { AuthController(get(), get(), get()) }

    factory { AuthViewModel() }
    factory { LoginViewModel(get()) }
    factory { RegisterViewModel(get()) }
    factory { TreatmentsViewModel(get()) }
    factory { CreateTreatmentViewModel(get()) }
}