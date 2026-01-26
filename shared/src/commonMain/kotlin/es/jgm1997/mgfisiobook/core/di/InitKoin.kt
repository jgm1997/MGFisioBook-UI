package es.jgm1997.mgfisiobook.core.di

import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module

fun initKoin(specModule: Module) {
    try {
        stopKoin()
    } catch (e: Exception) {
        println(e.message)
    }

    startKoin {
        modules(appModule, specModule)
    }
}