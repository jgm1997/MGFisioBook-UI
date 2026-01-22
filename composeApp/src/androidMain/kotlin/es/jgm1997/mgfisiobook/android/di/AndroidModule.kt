package es.jgm1997.mgfisiobook.android.di

import es.jgm1997.mgfisiobook.AndroidPushTokenProvider
import es.jgm1997.shared.push.PushTokenProvider
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val androidModule = module {
    single<PushTokenProvider> { AndroidPushTokenProvider(androidContext()) }
}