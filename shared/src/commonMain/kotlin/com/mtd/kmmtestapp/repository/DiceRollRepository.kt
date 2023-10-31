package com.mtd.kmmtestapp.repository

import co.touchlab.kermit.Logger
import com.mtd.kmmtestapp.data.models.DiceEntity
import com.mtd.kmmtestapp.db.RollEntity
import com.mtd.kmmtestapp.local.DiceRollLocalSource
import com.mtd.kmmtestapp.models.DiceSides
import com.mtd.kmmtestapp.models.RollInfoModel
import com.mtd.kmmtestapp.remote.DiceRollRemoteSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
class DiceRollRepository : KoinComponent {
    private val diceRollRemoteSource: DiceRollRemoteSource by inject()
    private val diceRollLocalSource: DiceRollLocalSource by inject()

    private val logger = Logger.withTag("DiceRollRepository")

    fun getDiceRolls(): Flow<List<RollInfoModel>> = diceRollLocalSource.getAllDiceRolls().transform {
        emit(
            it.map {
                RollInfoModel.fromEntity(it)
            }
        )
    }

    suspend fun rollDice(diceCount: Int, diceSides: DiceSides, roomSlug: String?): RollInfoModel {
        logger.v { "rollDice($diceCount, $diceSides)" }
        val newDiceRoll = diceRollRemoteSource.rollDice(diceCount, diceSides, roomSlug)
        logger.d { "Rolled $newDiceRoll" }

        val rollEntity = RollEntity(
            newDiceRoll.uuid,
            newDiceRoll.created_at,
            newDiceRoll.diceResults.map {
                DiceEntity(it.type, it.value)
            },
            newDiceRoll.total_value.toLong(),
            newDiceRoll.equation
        )

        diceRollLocalSource.insertDiceRoll(rollEntity)

        return RollInfoModel.fromEntity(rollEntity)
    }

    suspend fun clearLocalCache() = diceRollLocalSource.clearDatabase()
}