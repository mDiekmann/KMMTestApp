package com.mtd.kmmtestapp.viewModels

import com.mtd.kmmtestapp.models.DiceRoll
import com.mtd.kmmtestapp.models.DiceRollRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NewRollViewModel : CoroutineViewModel(), KoinComponent {
    private val diceRollRepo : DiceRollRepository by inject()

    private val _viewState = MutableStateFlow<NewRollViewState?>(null)
    val viewState: StateFlow<NewRollViewState?>
        get() = _viewState

    fun rollDice(diceCount: Int, diceSides: Int) {
        coroutineScope.launch {
            _viewState.value = NewRollViewState(isLoading = true)
            try {
                val newRoll = diceRollRepo.rollDice(diceCount, diceSides)
                _viewState.value = NewRollViewState(isLoading = true)
            } catch (e: Exception) {
                _viewState.value = NewRollViewState(error = "Error creating new roll")
            }
        }
    }
}

data class NewRollViewState(
    val latestRoll: DiceRoll? = null,
    val error: String? = null,
    val isLoading: Boolean = false,
)