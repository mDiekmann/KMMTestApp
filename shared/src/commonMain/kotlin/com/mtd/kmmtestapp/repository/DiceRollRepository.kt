package com.mtd.kmmtestapp.repository

import co.touchlab.kermit.Logger
import com.mtd.kmmtestapp.data.LocalCache
import com.mtd.kmmtestapp.models.DiceRoll
import com.mtd.kmmtestapp.network.DiceRollAPIInterface
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
class DiceRollRepository : KoinComponent {
    private val diceRollAPI: DiceRollAPIInterface by inject()
    private val localCache: LocalCache by inject()

    val logger = Logger.withTag("DiceRollRepository")
    
    fun getDiceRolls(): Flow<List<DiceRoll>> = localCache.getAllDiceRolls()

    suspend fun rollDice(diceCount: Int, diceSides: Int): DiceRoll {
        logger.v { "rollDice($diceCount, $diceSides)" }
        val diceRoll = diceRollAPI.rollDice(diceCount, diceSides)
        logger.d { "Rolled $diceRoll" }

        localCache.insertDiceRoll(diceRoll)

        return diceRoll
    }

    suspend fun clearLocalCache() = localCache.clearDatabase()
}