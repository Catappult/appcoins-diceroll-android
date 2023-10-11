package com.appcoins.diceroll.feature.roll_game.ui.payments.options

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appcoins.diceroll.feature.roll_game.data.usecases.GetAttemptsUseCase
import com.appcoins.diceroll.feature.roll_game.data.usecases.ResetAttemptsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PaymentsViewModel @Inject constructor(
  private val resetAttemptsUseCase: ResetAttemptsUseCase,
  private val getAttemptsUseCase: GetAttemptsUseCase,
) : ViewModel() {

  internal val uiState: StateFlow<PaymentsOptionsState> =
    getAttemptsUseCase()
      .map { attemptsLeft ->
        PaymentsOptionsState.Success(attemptsLeft)
      }
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = PaymentsOptionsState.Loading,
      )

  suspend fun resetAttemptsLeft() {
    resetAttemptsUseCase()
  }
}
