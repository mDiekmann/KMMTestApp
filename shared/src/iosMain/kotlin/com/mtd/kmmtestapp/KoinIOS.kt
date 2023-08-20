package com.mtd.kmmtestapp

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.mtd.kmmtestapp.db.Database
import org.koin.dsl.module

actual val platformModule = module {
    single<SqlDriver> {
        NativeSqliteDriver(Database.Schema,
            "test.db")
    }
}