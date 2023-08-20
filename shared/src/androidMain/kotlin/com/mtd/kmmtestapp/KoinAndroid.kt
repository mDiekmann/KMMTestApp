package com.mtd.kmmtestapp

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.mtd.kmmtestapp.db.Database
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single< SqlDriver> {
        AndroidSqliteDriver(
            Database.Schema,
            get(),
            "test.db")
    }
}