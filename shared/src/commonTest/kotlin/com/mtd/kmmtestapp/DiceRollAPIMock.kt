package com.mtd.kmmtestapp

import com.mtd.kmmtestapp.database.models.DiceRoll
import com.mtd.kmmtestapp.models.DiceSides
import com.mtd.kmmtestapp.network.DiceRollAPIInterface

class DiceRollAPIMock : DiceRollAPIInterface {
    override suspend fun rollDice(diceCount: Int, diceSides: DiceSides): DiceRoll {
        return TestUtil.makeDiceRoll(diceCount, diceSides)
    }
}