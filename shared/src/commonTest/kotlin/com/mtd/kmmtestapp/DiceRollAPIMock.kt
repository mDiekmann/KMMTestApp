package com.mtd.kmmtestapp

import com.mtd.kmmtestapp.database.models.DiceRoll
import com.mtd.kmmtestapp.models.DiceSides
import com.mtd.kmmtestapp.network.DiceRollAPIInterface
import com.mtd.kmmtestapp.network.models.RollResponseModel

class DiceRollAPIMock : DiceRollAPIInterface {
    override suspend fun rollDice(diceCount: Int, diceSides: DiceSides): DiceRoll {
        return TestUtil.makeDiceRoll(diceCount, diceSides)
    }

    override suspend fun rollDice(
        diceCount: Int,
        diceSides: DiceSides,
        roomSlug: String?
    ): RollResponseModel {
        TODO("Not yet implemented")
    }
}