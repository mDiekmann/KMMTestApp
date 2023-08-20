package com.mtd.kmmtestapp

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.inMemoryDriver
import com.mtd.kmmtestapp.db.Database

internal actual fun createTestDriver(): SqlDriver = inMemoryDriver(Database.Schema)