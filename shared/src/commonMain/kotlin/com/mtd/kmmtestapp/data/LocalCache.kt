package com.mtd.kmmtestapp.data

import com.mtd.kmmtestapp.entities.DiceRoll

internal class LocalCache(databaseDriverFactory: DriverFactory) {
    private val database = com.mtd.kmmtestapp.db.Database(databaseDriverFactory.createDriver())
    private val dbQuery = database.databaseQueries

    internal fun clearDatabase() {
        dbQuery.transaction {
            dbQuery.removeAllDiceRolls()
        }
    }

    internal fun getAllDiceRolls(): List<DiceRoll> {
        return dbQuery.selectAllDiceRolls(::mapDiceRollSelection).executeAsList()
    }

    private fun mapDiceRollSelection(
        input: String,
        result: Long,
        details: String,
        rollTimeUTC: String
    ): DiceRoll {
        return DiceRoll(
            input = input,
            result = result.toInt(),
            details = details,
            rollTimeUTC = rollTimeUTC
        )
    }

    internal fun insertDiceRolls(diceRolls: List<DiceRoll>) {
        dbQuery.transaction {
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
            rollTimeUTC = diceRoll.rollTimeUTC
        )
    }
}