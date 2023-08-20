package com.mtd.kmmtestapp

import com.mtd.kmmtestapp.model.DiceRoll
import com.mtd.kmmtestapp.network.DiceRollAPI

class DiceRollAPIMock : DiceRollAPI {
    override suspend fun rollDice(diceCount: Int, diceSides: Int): DiceRoll {
        return TestUtil.makeDiceRoll(diceCount, diceSides)
    }
}