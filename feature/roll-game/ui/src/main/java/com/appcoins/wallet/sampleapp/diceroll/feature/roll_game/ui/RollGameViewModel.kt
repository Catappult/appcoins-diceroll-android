package com.appcoins.wallet.sampleapp.diceroll.feature.roll_game.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appcoins.wallet.sampleapp.diceroll.feature.stats.data.model.DiceRoll
import com.appcoins.wallet.sampleapp.diceroll.feature.stats.data.usecases.GetAttemptsLeftUseCase
import com.appcoins.wallet.sampleapp.diceroll.feature.stats.data.usecases.SaveDiceRollUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class RollGameViewModel @Inject constructor(
  private val saveDiceRollUseCase: SaveDiceRollUseCase,
  private val getAttemptsLeftUseCase: GetAttemptsLeftUseCase
) : ViewModel() {

  internal val uiState: StateFlow<RollGameUiState> =
    getAttemptsLeftUseCase()
      .map { attemptsLeft ->
        RollGameUiState.Success(attemptsLeft)
      }
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = RollGameUiState.Loading,
      )

  private val _dialogState = MutableStateFlow<PaymentsDialogState?>(null)
  internal val dialogState: StateFlow<PaymentsDialogState?> get() = _dialogState

  suspend fun saveDiceRoll(diceRoll: DiceRoll) {
    saveDiceRollUseCase(diceRoll)
  }

  fun openPaymentsDialog() {
    _dialogState.value = PaymentsDialogState.Opened
  }

  fun closePaymentsDialog() {
    _dialogState.value = PaymentsDialogState.Closed
  }
}

sealed interface RollGameUiState {
  data object Loading : RollGameUiState
  data class Success(
    val attemptsLeft: Int?
  ) : RollGameUiState
}

sealed interface PaymentsDialogState {
  data object Opened : PaymentsDialogState
  data object Closed : PaymentsDialogState
}
