package com.appcoins.wallet.sampleapp.diceroll.feature.stats.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
internal fun StatsRoute(
  viewModel: RollGameViewModel = hiltViewModel(),
) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()
  StatsScreen(uiState = uiState)
}

@Composable
fun StatsScreen(
  uiState: StatsUiState,
) {

}