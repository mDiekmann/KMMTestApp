package com.mtd.kmmtestapp.network

import com.mtd.kmmtestapp.model.DiceRoll

interface DiceRollAPI {
    suspend fun rollDice(diceCount: Int, diceSides: Int): DiceRoll
}