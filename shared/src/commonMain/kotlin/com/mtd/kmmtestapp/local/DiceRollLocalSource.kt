package com.mtd.kmmtestapp.local

import com.mtd.kmmtestapp.database.AppDatabase
import com.mtd.kmmtestapp.database.models.DiceRoll
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DiceRollLocalSource: KoinComponent {
    private val appDatabase: AppDatabase by inject()

    internal suspend fun clearDatabase() = appDatabase.clearDatabase()
    internal fun getAllDiceRolls() = appDatabase.getAllDiceRolls()
    internal suspend fun insertDiceRolls(diceRolls: List<DiceRoll>) = appDatabase.insertDiceRolls(diceRolls)
    internal fun insertDiceRoll(diceRoll: DiceRoll) = appDatabase.insertDiceRoll(diceRoll)

}