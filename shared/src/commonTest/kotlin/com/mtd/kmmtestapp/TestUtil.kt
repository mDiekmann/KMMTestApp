package com.mtd.kmmtestapp

import com.mtd.kmmtestapp.database.models.DiceEntity
import com.mtd.kmmtestapp.db.RollEntity
import com.mtd.kmmtestapp.models.DiceSides
import kotlinx.datetime.Clock

class TestUtil {
    companion object {
        fun makeDiceRoll(diceCount: Int, diceSides: DiceSides): RollEntity {
            var results: MutableList<DiceEntity> = ArrayList()
            var total: Long = 0
            val diceSideValues = (1..diceSides.value)
            for (i in 1..diceCount) {
                val result = diceSideValues.random()
                total += result
                results.add(DiceEntity(diceSides, result))
            }
            val uuid = ""
            val input = "${diceCount}d${diceSides}"
            val rollList = results.joinToString(" +", "(", ")")
            val timeStamp = Clock.System.now().toString()

            return RollEntity(
                uuid,
                timeStamp,
                results,
                total,
                input
            )
        }
    }
}