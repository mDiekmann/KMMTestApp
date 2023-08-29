package com.mtd.kmmtestapp.viewModels

import com.mtd.kmmtestapp.models.DiceRoll
import com.mtd.kmmtestapp.repository.DiceRollRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RollHistoryViewModel: CoroutineViewModel(), KoinComponent {
    private val diceRollRepo : DiceRollRepository by inject()

    private lateinit var _diceRolls: StateFlow<List<DiceRoll>>
    val diceRolls: StateFlow<List<DiceRoll>>
        get() = _diceRolls

    init {
        getDiceRolls()
    }

    private fun getDiceRolls() {
        coroutineScope.launch {
            _diceRolls = diceRollRepo.getDiceRolls().stateIn(
                coroutineScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )
        }
    }

    fun clearDiceRolls() {
        coroutineScope.launch {
            diceRollRepo.clearLocalCache()
        }
    }
}