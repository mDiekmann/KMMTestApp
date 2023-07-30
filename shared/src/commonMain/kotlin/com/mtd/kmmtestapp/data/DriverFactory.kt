package com.mtd.kmmtestapp.data

import app.cash.sqldelight.db.SqlDriver
import com.mtd.kmmtestapp.db.Database

expect class DriverFactory {
    fun createDriver(): SqlDriver
}

fun createDatabase(driverFactory: DriverFactory): Database {
    val driver = driverFactory.createDriver()
    val database = Database(driver)

    return database
}