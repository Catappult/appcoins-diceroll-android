package com.appcoins.wallet.sampleapp.diceroll.feature.stats.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.appcoins.wallet.sampleapp.diceroll.core.design.theme.*
import com.appcoins.wallet.sampleapp.diceroll.core.utils.R
import com.appcoins.wallet.sampleapp.diceroll.core.utils.extensions.toPercent
import com.appcoins.wallet.sampleapp.diceroll.core.utils.widgets.Loading
import com.appcoins.wallet.sampleapp.diceroll.feature.stats.data.model.DiceRoll

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
  when (uiState) {
    StatsUiState.Loading -> {
      Loading(R.string.loading)
    }
    is StatsUiState.Success -> {
      StatsContent(
        diceRollList = uiState.diceRollList,
      )
    }
  }
}

@Composable
fun StatsContent(diceRollList: List<DiceRoll>) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp)
      .verticalScroll(rememberScrollState())
  ) {
    StatsHeaderContent(diceRollList)
    StatsDonutChart(diceRollList)
    StatsBarChart(diceRollList)
  }
}

@Composable
fun StatsHeaderContent(diceRollList: List<DiceRoll>) {
  Column(
    modifier = Modifier
      .fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .padding(4.dp)
    ) {
      StatsItem(
        statValue = diceRollList.size.toString(),
        statTitle = stringResource(id = R.string.stats_games_played)
      )
    }
    Row(
      modifier = Modifier
        .fillMaxWidth(),
    ) {
      Card(
        modifier = Modifier
          .padding(4.dp)
          .weight(1f)
      ) {
        StatsItem(
          statValue = diceRollList.filter { it.rollWin }.size.toString(),
          statTitle = stringResource(id = R.string.stats_games_won)
        )
      }
      Card(
        modifier = Modifier
          .padding(4.dp)
          .weight(1f)
      ) {
        StatsItem(
          statValue = winPercentage(diceRollList),
          statTitle = stringResource(id = R.string.stats_win_percentage)
        )
      }
    }
  }
}

@Composable
fun StatsItem(
  statValue: String,
  statTitle: String
) {
  Box(
    modifier = Modifier.fillMaxWidth(),
    contentAlignment = Alignment.Center
  ) {
    Column(
      modifier = Modifier.padding(8.dp),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Text(
        text = statValue,
        style = MaterialTheme.typography.headlineLarge
      )
      Text(
        text = statTitle,
        style = MaterialTheme.typography.bodyLarge
      )
    }
  }
}

@Composable
fun StatsDonutChart(diceRollList: List<DiceRoll>) {
  val resultDistribution = diceRollList.groupingBy { it.resultNumber }.eachCount()
  val guessDistribution = diceRollList.groupingBy { it.guessNumber }.eachCount()
  val resultPercentageDistribution = resultDistribution.map { it.value.toFloat() }.toPercent()

  DonutChart(
    colors = listOf(
      chart_tone1,
      chart_tone2,
      chart_tone3,
      chart_tone4,
      chart_tone5,
      chart_tone6,
    ),
    inputValues = resultPercentageDistribution,
    textColor = MaterialTheme.colorScheme.primary
  )
}

@Composable
fun StatsBarChart(diceRollList: List<DiceRoll>) {
  val resultDistribution = diceRollList.groupingBy { it.resultNumber }.eachCount()
  val guessDistribution = diceRollList.groupingBy { it.guessNumber }.eachCount()
  val resultPercentageDistribution = resultDistribution.map { it.value.toFloat() }.toPercent()

  BarChart(
    values = resultPercentageDistribution
  )
}

private fun winPercentage(diceRollList: List<DiceRoll>): String {
  val winCount = diceRollList.filter { it.rollWin }.size
  val totalCount = diceRollList.size
  return if (totalCount == 0) {
    "0"
  } else {
    (winCount * 100 / totalCount).toString()
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