package com.appcoins.wallet.sampleapp.diceroll.feature.roll_game.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
internal fun RollGameRoute(
  viewModel: RollGameViewModel = hiltViewModel(),
) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()
  RollGameScreen(uiState = uiState)
}

@Composable
fun RollGameScreen(
  uiState: RollGameUiState,
) {

}