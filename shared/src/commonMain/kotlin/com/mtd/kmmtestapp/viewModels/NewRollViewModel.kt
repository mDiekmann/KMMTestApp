package com.mtd.kmmtestapp.viewModels

import co.touchlab.kermit.Logger
import com.mtd.kmmtestapp.UserSettings
import com.mtd.kmmtestapp.models.DiceSides
import com.mtd.kmmtestapp.repository.DiceRollRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


open class NewRollViewModel() : ViewModel(), KoinComponent {
    private val diceRollRepo : DiceRollRepository by inject()
    private val userSettings : UserSettings by inject()
    private val logger = Logger.withTag("NewRollViewModel")

    private val mutableViewState: MutableStateFlow<NewRollViewState> = MutableStateFlow(NewRollViewState())
    val viewState: StateFlow<NewRollViewState>
        get() = mutableViewState.asStateFlow()

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

    private val _diceCountInput = MutableStateFlow<Int>(possibleDiceCounts.first())
    val diceCountInput: StateFlow<Int>
        get() = _diceCountInput.asStateFlow()

    private val _diceSidesInput = MutableStateFlow<DiceSides>(possibleDiceSides.first())
    val diceSidesInput: StateFlow<DiceSides>
        get() = _diceSidesInput.asStateFlow()


    fun updateDiceCount(diceCount: Int) {
        _diceCountInput.value = diceCount
    }

    fun updateDiceSides(diceSides: DiceSides) {
        _diceSidesInput.value = diceSides
    }

    suspend fun rollDice() {
        logger.d { "rollDice(${diceCountInput.value}, ${diceSidesInput.value})" }

        // set loading state, will be cleared on repository update
        setLoadingState(true)

        try {
            val newRoll = diceRollRepo.rollDice(
                diceCountInput.value,
                diceSidesInput.value,
                userSettings.getRoomSlug()
            )
            mutableViewState.value = NewRollViewState(
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

    private fun setLoadingState(isLoading: Boolean) {
        mutableViewState.value = mutableViewState.value.copy(isLoading= isLoading)
    }

    private fun setErrorState(error: String?) {
        mutableViewState.value = mutableViewState.value.copy(isLoading= false, error = error)
    }
}

/*
using an all sealed versus data class has it's trade offs

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
