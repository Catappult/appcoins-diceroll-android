package com.appcoins.wallet.sampleapp.diceroll.feature.roll_game.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appcoins.wallet.sampleapp.diceroll.feature.settings.data.UserPrefs
import com.appcoins.wallet.sampleapp.diceroll.feature.settings.data.UserPrefsDataSource
import com.appcoins.wallet.sampleapp.diceroll.feature.stats.data.model.DiceRoll
import com.appcoins.wallet.sampleapp.diceroll.feature.stats.data.usecases.GetAttemptsLeftUseCase
import com.appcoins.wallet.sampleapp.diceroll.feature.stats.data.usecases.GetDiceRollsUseCase
import com.appcoins.wallet.sampleapp.diceroll.feature.stats.data.usecases.SaveDiceRollUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class RollGameViewModel @Inject constructor(
  private val saveDiceRollUseCase: SaveDiceRollUseCase,
  private val getAttemptsLeftUseCase: GetAttemptsLeftUseCase
) : ViewModel() {

  val uiState: StateFlow<RollGameUiState> =
    getAttemptsLeftUseCase()
      .map { attemptsLeft ->
      RollGameUiState.Success(attemptsLeft)
    }
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = RollGameUiState.Loading,
      )

  suspend fun saveDiceRoll(diceRoll: DiceRoll) {
    saveDiceRollUseCase(diceRoll)
  }
}

sealed interface RollGameUiState {
  object Loading : RollGameUiState
  data class Success(
    val attemptsLeft: Int?
  ) : RollGameUiState
}
