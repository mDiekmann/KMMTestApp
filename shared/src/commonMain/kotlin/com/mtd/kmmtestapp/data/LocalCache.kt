package com.mtd.kmmtestapp.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.db.SqlDriver
import com.mtd.kmmtestapp.db.Database
import com.mtd.kmmtestapp.model.DiceRoll
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

internal class LocalCache(
    sqlDriver: SqlDriver,
    private val backgroundDispatcher: CoroutineDispatcher
) {
    private val database = Database(sqlDriver)
    private val dbQuery = database.databaseQueries

    suspend internal fun clearDatabase() {
        dbQuery.transactionWithContext(backgroundDispatcher) {
            dbQuery.removeAllDiceRolls()
        }
    }

    internal fun getAllDiceRolls(): Flow<List<DiceRoll>> {
        return dbQuery
            .selectAllDiceRolls(::mapDiceRollSelection)
            .asFlow()
            .mapToList(Dispatchers.Default)
            .flowOn(backgroundDispatcher)
    }

    /// Map from DB entity to data model
    private fun mapDiceRollSelection(
        input: String,
        result: Long,
        details: String,
        rollTimeEpoch: Long
    ): DiceRoll {
        return DiceRoll(
            input = input,
            result = result.toInt(),
            details = details,
            rollTimeEpoch = rollTimeEpoch
        )
    }

    suspend internal fun insertDiceRolls(diceRolls: List<DiceRoll>) {
        dbQuery.transactionWithContext(backgroundDispatcher) {
            diceRolls.forEach { diceRoll ->
                insertDiceRoll(diceRoll)
            }
        }
    }

    internal fun insertDiceRoll(diceRoll: DiceRoll) {
        dbQuery.insertDiceRoll(
            input = diceRoll.input,
            result = diceRoll.result.toLong(),
            details = diceRoll.details,
            rollTimeEpoch = diceRoll.rollTimeEpoch
        )
    }
}