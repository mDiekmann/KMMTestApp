package com.mtd.kmmtestapp.di

import androidx.preference.PreferenceManager
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.mtd.kmmtestapp.AndroidUserSettings
import com.mtd.kmmtestapp.UserSettings
import com.mtd.kmmtestapp.db.Database
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.android.Android
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<SqlDriver> {
        AndroidSqliteDriver(
            Database.Schema,
            get(),
            "test.db")
    }
    single<HttpClientEngine> {
        Android.create()
    }
    single<UserSettings> {
        AndroidUserSettings(PreferenceManager.getDefaultSharedPreferences(get()))
    }
}