package com.mtd.kmmtestapp.viewModels

import co.touchlab.kermit.Logger
import com.mtd.kmmtestapp.models.DiceSides
import com.mtd.kmmtestapp.repository.DiceRollRepository
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmm.viewmodel.coroutineScope
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class NewRollViewModel : KMMViewModel(), KoinComponent {
    private val diceRollRepo : DiceRollRepository by inject()
    private val logger = Logger.withTag("NewRollViewModel")

    private val _viewState = MutableStateFlow<NewRollViewState>(viewModelScope, NewRollViewState())
    @NativeCoroutinesState
    val viewState: StateFlow<NewRollViewState>
        get() = _viewState.asStateFlow()

    val minDiceCount = 1
    val maxDiceCount = 100
    val possibleDiceCounts: List<Int> = (minDiceCount..maxDiceCount).toList()
    val possibleDiceSides: List<DiceSides> = listOf(
        DiceSides.d4,
        DiceSides.d6,
        DiceSides.d8,
        DiceSides.d12,
        DiceSides.d20,
        DiceSides.d100
    )

    private val _diceCountInput = MutableStateFlow<Int>(viewModelScope, possibleDiceCounts.first())
    @NativeCoroutinesState
    val diceCountInput: StateFlow<Int>
        get() = _diceCountInput.asStateFlow()

    private val _diceSidesInput = MutableStateFlow<DiceSides>(viewModelScope, possibleDiceSides.first())


    @NativeCoroutinesState
    val diceSidesInput: StateFlow<DiceSides>
        get() = _diceSidesInput.asStateFlow()


    fun updateDiceCount(diceCount: Int) {
        _diceCountInput.value = diceCount
    }

    fun updateDiceSides(diceSides: DiceSides) {
        _diceSidesInput.value = diceSides
    }

    fun rollDice() {
        logger.d { "rollDice(${diceCountInput.value}, ${diceSidesInput.value})" }

        viewModelScope.coroutineScope.launch {
            setLoadingState(true)
            try {
                val newRoll = diceRollRepo.rollDice(diceCountInput.value, diceSidesInput.value)

                _viewState.value = NewRollViewState(
                    LatestRollState.LastSuccessfulRoll(
                    "Last Roll (${newRoll.equation}): ${newRoll.total}",
                    newRoll.resultsArr.contentToString()
                )
                )
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

sealed class LatestRollState() {
    object Initial: LatestRollState()
    data class LastSuccessfulRoll(val rollValue: String, val rollDetails: String): LatestRollState()
}

data class NewRollViewState(
    val latestRollViewState: LatestRollState = LatestRollState.Initial,
    val error: String? = null,
    val isLoading: Boolean = false,
)
