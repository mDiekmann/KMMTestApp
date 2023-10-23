package com.mtd.kmmtestapp.remote

import com.mtd.kmmtestapp.models.DiceSides
import com.mtd.kmmtestapp.network.DiceRollAPIInterface
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DiceRollRemoteSource: KoinComponent {
    private val diceRollAPI: DiceRollAPIInterface by inject()

    suspend fun rollDice(diceCount: Int, diceSides: DiceSides, roomSlug: String?) = diceRollAPI.rollDice(diceCount, diceSides, roomSlug)
}