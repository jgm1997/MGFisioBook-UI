package es.jgm1997.mgfisiobook.di

import es.jgm1997.mgfisiobook.IosPushTokenProvider
import es.jgm1997.mgfisiobook.shared.push.PushTokenProvider
import org.koin.dsl.module

val iosModule = module {
    single<PushTokenProvider> { IosPushTokenProvider() }
}