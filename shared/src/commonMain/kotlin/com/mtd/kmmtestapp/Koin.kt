package com.mtd.kmmtestapp

import com.mtd.kmmtestapp.data.LocalCache
import kotlinx.coroutines.Dispatchers
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

fun initKoin(appModule: Module): KoinApplication {
    val koinApplication = startKoin {
        modules(
            appModule,
            databaseModule,
            apiModule
        )
    }

    return koinApplication
}

private val databaseModule = module {
    single {
        LocalCache(
            get(),
            Dispatchers.Default
        )
    }
}

private val apiModule = module {
    single {
        LocalCache(
            get(),
            Dispatchers.Default
        )
    }
}


expect val platformModule: Module