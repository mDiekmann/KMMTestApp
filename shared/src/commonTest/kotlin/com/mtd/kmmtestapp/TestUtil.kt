package com.mtd.kmmtestapp

import com.mtd.kmmtestapp.database.models.DiceRoll
import com.mtd.kmmtestapp.models.DiceSides
import kotlinx.datetime.Clock

class TestUtil {
    companion object {
        fun makeDiceRoll(diceCount: Int, diceSides: DiceSides): DiceRoll {
            var results: MutableList<Int> = ArrayList()
            val diceSideValues = (1..diceSides.value)
            for (i in 1..diceCount) {
                results.add(diceSideValues.random())
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