package com.appcoins.wallet.sampleapp.diceroll.feature.stats.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.appcoins.wallet.sampleapp.diceroll.core.design.theme.DiceRollTheme
import com.appcoins.wallet.sampleapp.diceroll.feature.stats.data.model.DiceRoll

@Composable
internal fun RollDetailsStatsRoute(
  viewModel: RollGameViewModel = hiltViewModel(),
) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()
  RollDetailsStatsScreen(uiState = uiState)
}

@Composable
fun RollDetailsStatsScreen(
  uiState: StatsUiState
) {
  when (uiState) {
    StatsUiState.Loading -> {
      Loading(R.string.loading)
    }
    is StatsUiState.Success -> {
      RollDetailsStatsContent(
        diceRollList = uiState.diceRollList,
      )
    }
  }
}

@Composable
fun RollDetailsStatsScreen(diceRollList: List<DiceRoll>) {
  LazyColumn(
  ) {
    items(diceRollList) { roll ->
      DiceRollItem(roll)
    }
  }
}

@Composable
fun DiceRollItem(roll: DiceRoll) {
  var isExpanded by rememberSaveable { mutableStateOf(false) }
  Card(
    shape = MaterialTheme.shapes.large,
    modifier = Modifier
      .fillMaxWidth()
      .padding(top = 8.dp)
      .clickable {
        isExpanded = !isExpanded
      },
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      verticalAlignment = Alignment.CenterVertically,
    ) {

      Text(
        text = roll.guessNumber.toString(),
        style = MaterialTheme.typography.bodyLarge
      )
      Text(
        text = roll.resultNumber.toString(),
        style = MaterialTheme.typography.bodyLarge
      )

    }
    AnimatedVisibility(visible = isExpanded) {
//      CarDetails(carItem)
    }
  }
}


@Preview
@Composable
private fun PreviewStatsContent() {
  DiceRollTheme(darkTheme = false) {
    StatsContent(
      diceRollList = listOf(
        DiceRoll(1, true, 1, 1, 2),
        DiceRoll(2, false, 1, 6, 2),
        DiceRoll(3, false, 1, 4, 2),
      )
    )
  }
}