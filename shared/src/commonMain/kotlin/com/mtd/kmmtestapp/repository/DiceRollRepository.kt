package com.mtd.kmmtestapp.repository

import co.touchlab.kermit.Logger
import com.mtd.kmmtestapp.database.AppDatabase
import com.mtd.kmmtestapp.database.models.DiceRoll
import com.mtd.kmmtestapp.local.DiceRollLocalSource
import com.mtd.kmmtestapp.models.DiceSides
import com.mtd.kmmtestapp.network.DiceRollAPIInterface
import com.mtd.kmmtestapp.remote.DiceRollRemoteSource
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
class DiceRollRepository : KoinComponent {
    private val diceRollRemoteSource: DiceRollRemoteSource by inject()
    private val diceRollLocalSource: DiceRollLocalSource by inject()

    private val logger = Logger.withTag("DiceRollRepository")

    @NativeCoroutines
    fun getDiceRolls(): Flow<List<DiceRoll>> = diceRollLocalSource.getAllDiceRolls()

    @NativeCoroutines
    suspend fun rollDice(diceCount: Int, diceSides: DiceSides): DiceRoll {
        logger.v { "rollDice($diceCount, $diceSides)" }
        val diceRoll = diceRollRemoteSource.rollDice(diceCount, diceSides)
        logger.d { "Rolled $diceRoll" }

        diceRollLocalSource.insertDiceRoll(diceRoll)

        return diceRoll
    }

    @NativeCoroutines
    suspend fun clearLocalCache() = diceRollLocalSource.clearDatabase()
}