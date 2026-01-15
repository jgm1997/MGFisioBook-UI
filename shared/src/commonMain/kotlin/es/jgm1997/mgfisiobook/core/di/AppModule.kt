package es.jgm1997.mgfisiobook.core.di

import es.jgm1997.mgfisiobook.core.auth.AuthRepository
import es.jgm1997.mgfisiobook.core.network.HttpClientFactory
import org.koin.dsl.module

val appModule = module {
    single { HttpClientFactory().create() }
    single { AuthRepository(get()) }
}