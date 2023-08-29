package com.mtd.kmmtestapp.viewModels

import co.touchlab.kermit.Logger
import com.mtd.kmmtestapp.models.DiceRoll
import com.mtd.kmmtestapp.repository.DiceRollRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NewRollViewModel : CoroutineViewModel(), KoinComponent {
    private val diceRollRepo : DiceRollRepository by inject()
    val logger = Logger.withTag("NewRollViewModel")

    private val _viewState = MutableStateFlow<NewRollViewState?>(null)
    val viewState: StateFlow<NewRollViewState?>
        get() = _viewState

    val minDiceCount = 1
    val maxDiceCount = 100
    val possibleDiceSides = listOf(4, 6, 8, 12, 20, 100)

    fun rollDice(diceCount: Int, diceSides: Int) {
        logger.d { "rollDice($diceCount, $diceSides)" }
        coroutineScope.launch {
            _viewState.value = NewRollViewState(isLoading = true)
            try {
                val newRoll = diceRollRepo.rollDice(diceCount, diceSides)
                _viewState.value = NewRollViewState(latestRoll = newRoll)
            } catch (e: Exception) {
                logger.e(e) { "Exception during rollDice: $e" }
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