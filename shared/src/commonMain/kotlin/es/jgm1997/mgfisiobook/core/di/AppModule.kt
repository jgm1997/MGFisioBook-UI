package es.jgm1997.mgfisiobook.core.di

import es.jgm1997.mgfisiobook.core.auth.AuthRepository
import es.jgm1997.mgfisiobook.core.network.HttpClientFactory
import es.jgm1997.mgfisiobook.core.treatments.TreatmentRepository
import es.jgm1997.mgfisiobook.features.login.LoginViewModel
import es.jgm1997.mgfisiobook.features.register.RegisterViewModel
import es.jgm1997.mgfisiobook.features.treatments.TreatmentsViewModel
import org.koin.dsl.module

val appModule = module {
    single { HttpClientFactory().create() }
    single { AuthRepository(get()) }
    single { TreatmentRepository(get()) }

    factory { LoginViewModel(get()) }
    factory { RegisterViewModel(get()) }
    factory { TreatmentsViewModel(get()) }
}