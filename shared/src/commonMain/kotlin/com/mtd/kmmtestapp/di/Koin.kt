package com.mtd.kmmtestapp.di

import com.mtd.kmmtestapp.database.AppDatabase
import com.mtd.kmmtestapp.network.DiceRollAPI
import com.mtd.kmmtestapp.network.DiceRollAPIInterface
import com.mtd.kmmtestapp.remote.DiceRollRemoteSource
import com.mtd.kmmtestapp.repository.DiceRollRepository
import kotlinx.coroutines.Dispatchers
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) {
    val koinApplication = startKoin {
        appDeclaration()
        modules(
            appModules
        )
    }

    // doOnStartup is a lambda which can be implemented in Swift on iOS side
    // currently unused, keeping in for potential future use
    // val koin = koinApplication.koin
    //val doOnStartup = koin.get<() -> Unit>()
    //doOnStartup.invoke()
}

/*
 using get() here is a workaround for a Kotlin issue
 could be fixed by moving to bottom of fil, but since we are using expect
 platformModule is declared in platform specific areas
 tracked at: https://github.com/InsertKoinIO/koin/issues/1341
 */
val appModules: Module get() = module  {
    includes(remoteModule, dispatcherModule, databaseModule, repositoryModule, platformModule)
}

val remoteModule = module {
    single<DiceRollAPIInterface> {
        DiceRollAPI(
            get()
        )
    }

    single<DiceRollRemoteSource> {
        DiceRollRemoteSource()
    }
}

val databaseModule = module {
    single {
        AppDatabase(
            get(),
            get()
        )
    }
}

val dispatcherModule = module {
    factory { Dispatchers.Default }
}

val repositoryModule = module {
    single {
        DiceRollRepository()
    }
}

expect val platformModule: Module