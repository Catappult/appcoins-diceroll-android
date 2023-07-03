package com.appcoins.wallet.sampleapp.diceroll.feature.roll_game.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appcoins.wallet.sampleapp.diceroll.feature.settings.data.UserPrefs
import com.appcoins.wallet.sampleapp.diceroll.feature.settings.data.UserPrefsDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class RollGameViewModel @Inject constructor(
  userPrefs: UserPrefsDataSource
) : ViewModel() {

  val uiState: StateFlow<RollGameUiState> =
    userPrefs.getUserPrefs().map { userPrefs ->
      RollGameUiState.Success(
        userPrefs = userPrefs
      )
    }
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = RollGameUiState.Loading,
      )
}

sealed interface RollGameUiState {
  object Loading : RollGameUiState
  data class Success(
    val userPrefs: UserPrefs
  ) : RollGameUiState
}
