package com.mtd.kmmtestapp.network

import com.mtd.kmmtestapp.models.DiceRoll

interface DiceRollAPIInterface {
    suspend fun rollDice(diceCount: Int, diceSides: Int): DiceRoll
}