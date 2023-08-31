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

    private val _viewState = MutableStateFlow<NewRollViewState>(NewRollViewState(latestRoll = null))
    val viewState: StateFlow<NewRollViewState>
        get() = _viewState

    val minDiceCount = 1
    val maxDiceCount = 100
    val possibleDiceSides = listOf(4, 6, 8, 12, 20, 100)

    fun rollDice(diceCount: Int, diceSides: Int) {
        logger.d { "rollDice($diceCount, $diceSides)" }
        coroutineScope.launch {
            setLoadingState(isLoading = true)
            try {
                val newRoll = diceRollRepo.rollDice(diceCount, diceSides)
                // s
                _viewState.value = NewRollViewState(latestRoll = newRoll)
            } catch (e: Exception) {
                logger.e(e) { "Exception during rollDice: $e" }
                setErrorState("Error creating new roll")
            }
        }
    }

    private fun setLoadingState(isLoading: Boolean) {
        _viewState.value = _viewState.value.copy(isLoading= isLoading)
    }

    private fun setErrorState(error: String?) {
        _viewState.value = _viewState.value.copy(isLoading= false, error = error)
    }
}

/*
using an all encompassing versus individual values has it's trade offs

on the plus side setting latest roll or error will reset isLoading automatically

on the negative side if special setters aren't created for a given state that uses copy()
individual states can be cleared out as a side effect
 */

data class NewRollViewState(
    val latestRoll: DiceRoll? = null,
    val error: String? = null,
    val isLoading: Boolean = false,
)