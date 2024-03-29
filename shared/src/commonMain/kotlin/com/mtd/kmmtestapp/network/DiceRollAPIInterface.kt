package com.mtd.kmmtestapp.network

import com.mtd.kmmtestapp.models.DiceSides
import com.mtd.kmmtestapp.network.models.RollResponseModel

interface DiceRollAPIInterface {
    suspend fun rollDice(diceCount: Int, diceSides: DiceSides, roomSlug: String?): RollResponseModel
}