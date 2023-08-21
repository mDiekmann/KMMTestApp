package com.mtd.kmmtestapp.models

import com.mtd.kmmtestapp.data.LocalCache
import com.mtd.kmmtestapp.network.DiceRollAPIImpl

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
class DiceRollRepository : KoinComponent {
    private val diceRollAPI: DiceRollAPIImpl by inject()
    private val localCache: LocalCache by inject()
}