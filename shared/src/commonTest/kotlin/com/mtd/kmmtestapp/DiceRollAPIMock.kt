package com.mtd.kmmtestapp

import com.mtd.kmmtestapp.models.DiceRoll
import com.mtd.kmmtestapp.network.DiceRollAPIInterface

class DiceRollAPIMock : DiceRollAPIInterface {
    override suspend fun rollDice(diceCount: Int, diceSides: Int): DiceRoll {
        return TestUtil.makeDiceRoll(diceCount, diceSides)
    }
}