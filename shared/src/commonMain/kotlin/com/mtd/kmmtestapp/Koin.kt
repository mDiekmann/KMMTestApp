package com.mtd.kmmtestapp

import com.mtd.kmmtestapp.data.LocalCache
import kotlinx.coroutines.Dispatchers
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) {
    startKoin {
        appDeclaration()
        modules(
            coreModule
        )
    }
}

private val coreModule = module {
    single{
        LocalCache(
            get(),
            Dispatchers.Default
        )
    }
}

fun initKoin() = initKoin {}

expect val platformModule: Module