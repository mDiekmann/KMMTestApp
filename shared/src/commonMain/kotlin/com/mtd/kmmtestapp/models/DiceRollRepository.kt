package com.mtd.kmmtestapp.models

import com.mtd.kmmtestapp.data.LocalCache
import com.mtd.kmmtestapp.network.DiceRollAPIImpl
import kotlinx.coroutines.flow.Flow

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
class DiceRollRepository : KoinComponent {
    private val diceRollAPI: DiceRollAPIImpl by inject()
    private val localCache: LocalCache by inject()

    fun getDiceRolls(): Flow<List<DiceRoll>> = localCache.getAllDiceRolls()

    suspend fun rollDice(diceCount: Int, diceSides: Int) {
        val diceRoll = diceRollAPI.rollDice(diceCount, diceSides)

        localCache.insertDiceRoll(diceRoll)
    }

    suspend fun clearLocalCache() = localCache.clearDatabase()
}