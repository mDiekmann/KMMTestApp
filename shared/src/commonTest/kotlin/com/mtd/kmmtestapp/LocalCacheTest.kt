package com.mtd.kmmtestapp

import app.cash.sqldelight.db.SqlDriver
import com.mtd.kmmtestapp.data.LocalCache
import com.mtd.kmmtestapp.entities.DiceRoll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal expect fun createTestDriver(): SqlDriver

class LocalCacheTest {
    private lateinit var localCache: LocalCache
    private var testDriver = createTestDriver()

    @BeforeTest
    fun setup() {
        localCache = LocalCache(testDriver, Dispatchers.Default)
    }

    @AfterTest
    fun tearDown() {
        testDriver.close()
    }

    @Test
    fun `Select All Rolls Success Batch Insert`() = runTest {
        val rollsToBeAdded = listOf(
            makeDiceRoll(10, 12),
            makeDiceRoll(3, 6)
        )

        localCache.insertDiceRolls(rollsToBeAdded)
        val result = localCache.getAllDiceRolls().first()
        assertEquals(rollsToBeAdded, result)
    }

    @Test
    fun `Select All Rolls Success Single Insert`() = runTest {
        val rollsToBeAdded = listOf(
            makeDiceRoll(10, 12),
            makeDiceRoll(3, 6)
        )

        for (roll in rollsToBeAdded) {
            localCache.insertDiceRoll(roll)
        }

        val result = localCache.getAllDiceRolls().first()
        assertEquals(rollsToBeAdded, result)
    }

    @Test
    fun `Delete All Success`() = runTest {
        val rollsToBeAdded = listOf(
                makeDiceRoll(10, 12),
        makeDiceRoll(3, 6)
        )

        localCache.insertDiceRolls(rollsToBeAdded)
        assertTrue(localCache.getAllDiceRolls().first().isNotEmpty())

        localCache.clearDatabase()
        assertTrue(localCache.getAllDiceRolls().first().count() == 0)
    }

    private fun makeDiceRoll(diceCount: Int, diceSides: Int): DiceRoll {
        var results: MutableList<Int> = ArrayList()
        val diceValues = (1..diceSides)
        for (i in 1..diceCount) {
            results.add(diceValues.random())
        }
        val input = "${diceCount}d${diceSides}"
        val rollList = results.joinToString(" +", "(", ")")
        val total = results.sum()
        val timeStamp = Clock.System.now().epochSeconds

        return DiceRoll(
            input = input,
            result = total,
            details = rollList,
            rollTimeEpoch = timeStamp
        )
    }
}