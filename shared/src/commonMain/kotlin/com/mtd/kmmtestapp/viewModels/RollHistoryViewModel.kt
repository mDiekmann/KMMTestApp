package com.mtd.kmmtestapp.viewModels

import com.mtd.kmmtestapp.models.DiceRoll
import com.mtd.kmmtestapp.repository.DiceRollRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RollHistoryViewModel: CoroutineViewModel(), KoinComponent {
    private val diceRollRepo : DiceRollRepository by inject()

    private var _diceRolls = MutableStateFlow<List<DiceRoll>>(emptyList())
    val diceRolls: StateFlow<List<DiceRoll>>
        get() = _diceRolls

    init {
        observeDiceRolls()
    }

    private fun observeDiceRolls() {
        coroutineScope.launch {
            diceRollRepo.getDiceRolls().collect{ rolls ->
                _diceRolls.value = rolls
            }
        }
        /*coroutineScope.launch {
            _diceRolls.value = diceRollRepo.getDiceRolls()
            _diceRolls = diceRollRepo.getDiceRolls().stateIn(
                coroutineScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )
        }*/
    }

    fun clearDiceRolls() {
        coroutineScope.launch {
            diceRollRepo.clearLocalCache()
        }
    }
}