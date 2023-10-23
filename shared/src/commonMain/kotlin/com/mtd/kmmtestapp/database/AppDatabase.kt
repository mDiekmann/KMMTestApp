package com.mtd.kmmtestapp.database

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.db.SqlDriver
import co.touchlab.kermit.Logger
import com.mtd.kmmtestapp.database.adapters.DiceValuesAdapter
import com.mtd.kmmtestapp.db.Database
import com.mtd.kmmtestapp.db.RollEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.json.Json

internal class AppDatabase(
    sqlDriver: SqlDriver,
    private val backgroundDispatcher: CoroutineDispatcher
) {
    private val json by lazy { Json { ignoreUnknownKeys = false } }

    private val database = Database(
        sqlDriver,
        RollEntity.Adapter(
            DiceValuesAdapter(json)
        )
    )
    private val dbQuery = database.databaseQueries
    private val logger = Logger.withTag("LocalCache")

    internal suspend fun clearDatabase() {
        dbQuery.transactionWithContext(backgroundDispatcher) {
            dbQuery.removeAllDiceRolls()
        }
    }

    internal fun getAllDiceRolls(): Flow<List<RollEntity>> {
        return dbQuery
            .selectAllDiceRolls()
            .asFlow()
            .mapToList(Dispatchers.Default)
            .flowOn(backgroundDispatcher)
    }

    internal suspend fun insertDiceRolls(diceRolls: List<RollEntity>) {
        dbQuery.transactionWithContext(backgroundDispatcher) {
            diceRolls.forEach { diceRoll ->
                insertDiceRoll(diceRoll)
            }
        }
    }

    internal fun insertDiceRoll(diceRoll: RollEntity) {
        logger.v { "Storing $diceRoll" }
        dbQuery.insertDiceRoll(
            diceRoll.uuid,
            diceRoll.creationDate,
            diceRoll.diceValues,
            diceRoll.totalValue,
            diceRoll.rollEquation
        )
    }
}