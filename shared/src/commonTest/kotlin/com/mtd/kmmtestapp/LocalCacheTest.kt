package com.mtd.kmmtestapp

import app.cash.sqldelight.db.SqlDriver
import com.mtd.kmmtestapp.database.AppDatabase
import com.mtd.kmmtestapp.models.DiceSides
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal expect fun createTestDriver(): SqlDriver

class LocalCacheTest {
    private lateinit var appDatabase: AppDatabase
    private var testDriver = createTestDriver()

    @BeforeTest
    fun setup() {
        appDatabase = AppDatabase(testDriver, Dispatchers.Default)
    }

    @AfterTest
    fun tearDown() {
        testDriver.close()
    }

    @Test
    fun `Select All Rolls Success Batch Insert`() = runTest {
        val rollsToBeAdded = listOf(
            TestUtil.makeDiceRoll(10, DiceSides.d12),
            TestUtil.makeDiceRoll(3, DiceSides.d6)
        ).sortedByDescending { it.creationDate }

        appDatabase.insertDiceRolls(rollsToBeAdded)
        val result = appDatabase.getAllDiceRolls().first()
        assertEquals(rollsToBeAdded, result)
    }

    @Test
    fun `Select All Rolls Success Single Insert`() = runTest {
        val rollsToBeAdded = listOf(
            TestUtil.makeDiceRoll(10, DiceSides.d12),
            TestUtil.makeDiceRoll(3, DiceSides.d6)
        ).sortedByDescending { it.creationDate }

        for (roll in rollsToBeAdded) {
            appDatabase.insertDiceRoll(roll)
        }

        val result = appDatabase.getAllDiceRolls().first()
        assertEquals(rollsToBeAdded, result)
    }

    @Test
    fun `Delete All Success`() = runTest {
        val rollsToBeAdded = listOf(
            TestUtil.makeDiceRoll(10, DiceSides.d12),
            TestUtil.makeDiceRoll(3, DiceSides.d6)
        )

        appDatabase.insertDiceRolls(rollsToBeAdded)
        assertTrue(appDatabase.getAllDiceRolls().first().isNotEmpty())

        appDatabase.clearDatabase()
        assertTrue(appDatabase.getAllDiceRolls().first().count() == 0)
    }
}