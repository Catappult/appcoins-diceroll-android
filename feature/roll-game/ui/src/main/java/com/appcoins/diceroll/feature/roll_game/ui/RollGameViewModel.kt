package com.appcoins.diceroll.feature.roll_game.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appcoins.diceroll.feature.roll_game.data.usecases.GetAttemptsUseCase
import com.appcoins.diceroll.feature.roll_game.data.usecases.SaveAttemptsUseCase
import com.appcoins.diceroll.feature.payments.ui.PaymentsDialogState
import com.appcoins.diceroll.feature.stats.data.model.DiceRoll
import com.appcoins.diceroll.feature.stats.data.usecases.SaveDiceRollUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class RollGameViewModel @Inject constructor(
  private val saveDiceRollUseCase: SaveDiceRollUseCase,
  private val saveAttemptsUseCase: SaveAttemptsUseCase,
  private val getAttemptsUseCase: GetAttemptsUseCase,
) : ViewModel() {

  internal val uiState: StateFlow<RollGameState> =
    getAttemptsUseCase()
      .map { attemptsLeft ->
        RollGameState.Success(attemptsLeft)
      }
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = RollGameState.Loading,
      )

  private val _dialogState = MutableStateFlow<PaymentsDialogState>(PaymentsDialogState.Closed)
  internal val dialogState: StateFlow<PaymentsDialogState> get() = _dialogState

  suspend fun saveDiceRoll(diceRoll: DiceRoll) {
    saveDiceRollUseCase(diceRoll)
  }

  suspend fun saveAttemptsLeft(attemptsLeft: Int) {
    saveAttemptsUseCase(attemptsLeft)
  }

  fun openPaymentsDialog() {
    _dialogState.value = PaymentsDialogState.Opened
  }

  fun closePaymentsDialog() {
    _dialogState.value = PaymentsDialogState.Closed
  }
}