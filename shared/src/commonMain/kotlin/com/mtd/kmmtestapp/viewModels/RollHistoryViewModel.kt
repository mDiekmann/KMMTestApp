package com.mtd.kmmtestapp.viewModels

import com.mtd.kmmtestapp.models.DiceRoll
import com.mtd.kmmtestapp.repository.DiceRollRepository
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmm.viewmodel.coroutineScope
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RollHistoryViewModel: KMMViewModel(), KoinComponent {
    private val diceRollRepository : DiceRollRepository by inject()

    private var _diceRolls = MutableStateFlow<List<DiceRoll>>(viewModelScope, emptyList())
    @NativeCoroutinesState
    val diceRolls: StateFlow<List<DiceRoll>>
        get() = _diceRolls

    init {
        observeDiceRolls()
    }

    private fun observeDiceRolls() {
        //TODO: There must be a better way to do this
        viewModelScope.coroutineScope.launch {
            diceRollRepository.getDiceRolls().collect{ rolls ->
                _diceRolls.value = rolls
            }
        }
    }

    fun clearDiceRolls() {
        viewModelScope.coroutineScope.launch {
            diceRollRepository.clearLocalCache()
        }
    }
}