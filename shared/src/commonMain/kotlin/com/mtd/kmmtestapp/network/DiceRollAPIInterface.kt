package com.mtd.kmmtestapp.network

import com.mtd.kmmtestapp.database.models.DiceRoll
import com.mtd.kmmtestapp.models.DiceSides

interface DiceRollAPIInterface {
    suspend fun rollDice(diceCount: Int, diceSides: DiceSides): DiceRoll
}