package com.mtd.kmmtestapp.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.mtd.kmmtestapp.db.Database
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.dsl.module

actual val platformModule = module {
    single<SqlDriver> {
        NativeSqliteDriver(Database.Schema,
            "test.db")
    }

    single<HttpClientEngine> {
        Darwin.create()
    }
}