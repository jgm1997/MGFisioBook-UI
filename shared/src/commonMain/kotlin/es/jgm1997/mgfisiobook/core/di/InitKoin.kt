package es.jgm1997.mgfisiobook.core.di

import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

fun initKoin() {
    try {
        stopKoin()
    } catch (e: Exception) {
        println(e.message)
    }

    startKoin { modules(appModule) }
}