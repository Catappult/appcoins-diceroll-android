package com.appcoins.wallet.sampleapp.diceroll.feature.roll_game.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appcoins.wallet.sampleapp.diceroll.feature.settings.data.UserPrefs
import com.appcoins.wallet.sampleapp.diceroll.feature.settings.data.UserPrefsDataSource
import com.appcoins.wallet.sampleapp.diceroll.feature.stats.data.model.DiceRoll
import com.appcoins.wallet.sampleapp.diceroll.feature.stats.data.usecases.SaveDiceRollUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class RollGameViewModel @Inject constructor(
  private val saveDiceRollUseCase: SaveDiceRollUseCase
) : ViewModel() {

  suspend fun saveDiceRoll(diceRoll: DiceRoll) {
    saveDiceRollUseCase(diceRoll)
  }
}

sealed interface RollGameUiState {
  object Loading : RollGameUiState
  data class Success(
    val userPrefs: UserPrefs
  ) : RollGameUiState
}
