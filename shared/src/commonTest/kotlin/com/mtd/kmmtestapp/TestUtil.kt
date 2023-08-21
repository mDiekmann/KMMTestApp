package com.mtd.kmmtestapp

import com.mtd.kmmtestapp.models.DiceRoll
import kotlinx.datetime.Clock

class TestUtil {
    companion object {
        fun makeDiceRoll(diceCount: Int, diceSides: Int): DiceRoll {
            var results: MutableList<Int> = ArrayList()
            val diceValues = (1..diceSides)
            for (i in 1..diceCount) {
                results.add(diceValues.random())
            }
            val input = "${diceCount}d${diceSides}"
            val rollList = results.joinToString(" +", "(", ")")
            val total = results.sum()
            val timeStamp = Clock.System.now().epochSeconds

            return DiceRoll(
                input = input,
                result = total,
                details = rollList,
                rollTimeEpoch = timeStamp
            )
        }
    }
}