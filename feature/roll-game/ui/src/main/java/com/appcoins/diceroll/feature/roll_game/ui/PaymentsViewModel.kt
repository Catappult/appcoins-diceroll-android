package com.appcoins.diceroll.feature.roll_game.ui

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

  internal val uiState: StateFlow<PaymentsUiState> =
    getAttemptsUseCase()
      .map { attemptsLeft ->
        PaymentsUiState.Start(attemptsLeft)
      }
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = PaymentsUiState.Loading,
      )

  suspend fun resetAttemptsLeft() {
    resetAttemptsUseCase()
  }
}

sealed interface PaymentsUiState {
  data object Loading : PaymentsUiState
  data class Start(
    val attemptsLeft: Int?
  ) : PaymentsUiState

  data object Failed : PaymentsUiState
  data object UserCanceled : PaymentsUiState
  data object Success : PaymentsUiState
}
