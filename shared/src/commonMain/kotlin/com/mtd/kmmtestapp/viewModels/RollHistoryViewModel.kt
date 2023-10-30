package com.mtd.kmmtestapp.viewModels

import co.touchlab.kermit.Logger
import co.touchlab.skie.configuration.annotations.DefaultArgumentInterop
import com.mtd.kmmtestapp.models.RollInfoModel
import com.mtd.kmmtestapp.repository.DiceRollRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
/*
playing around with Sealed class for view state
Issues with the error use case in iOS but great for specifying the empty use-case
 */
class RollHistoryViewModel: ViewModel(), KoinComponent {
    private val diceRollRepository : DiceRollRepository by inject()
    private val logger = Logger.withTag("RollHistoryViewModel")

    private var mutableViewState: MutableStateFlow<RollHistoryViewState> = MutableStateFlow(
        RollHistoryViewState.Initial)

    val viewState: StateFlow<RollHistoryViewState> = mutableViewState.asStateFlow()

    suspend fun activate() {
        observeDiceRolls()
    }

    override fun onCleared() {
        logger.v("Clearing RollHistoryViewModel")
    }

    private suspend fun observeDiceRolls() {
        diceRollRepository.getDiceRolls().collect{ rolls ->
            mutableViewState.update {
                if (rolls.isNotEmpty()) {
                    RollHistoryViewState.Success(rolls)
                } else {
                    RollHistoryViewState.Empty()
                }
            }
        }
    }

    suspend fun clearDiceRolls() {
        diceRollRepository.clearLocalCache()
    }
}

sealed class RollHistoryViewState {
    abstract val isLoading: Boolean

    data object Initial : RollHistoryViewState() {
        override val isLoading: Boolean = true
    }

    data class Empty @DefaultArgumentInterop.Enabled constructor(
        override val isLoading: Boolean = false
    ) : RollHistoryViewState()

    data class Success @DefaultArgumentInterop.Enabled constructor(
        val rolls: List<RollInfoModel>,
        override val isLoading: Boolean = false
    ) : RollHistoryViewState()

    data class Error @DefaultArgumentInterop.Enabled constructor(
        val error: String,
        override val isLoading: Boolean = false
    ) : RollHistoryViewState()
}