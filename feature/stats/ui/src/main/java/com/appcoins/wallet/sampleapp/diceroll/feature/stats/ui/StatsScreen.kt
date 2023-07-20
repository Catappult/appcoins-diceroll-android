package com.appcoins.wallet.sampleapp.diceroll.feature.stats.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.appcoins.wallet.sampleapp.diceroll.core.design.theme.DiceRollTheme
import com.appcoins.wallet.sampleapp.diceroll.core.utils.widgets.Loading
import com.appcoins.wallet.sampleapp.diceroll.feature.stats.data.model.DiceRoll
import com.appcoins.wallet.sampleapp.diceroll.core.utils.R

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
  ) {
    StatsHeaderContent(diceRollList)
    StatsListContent(diceRollList)
  }
}

@Composable
fun StatsHeaderContent(diceRollList: List<DiceRoll>) {
  ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
    val (percentageCard, gamesColumn) = createRefs()
    Card(
      modifier = Modifier.
        padding(4.dp)
        .constrainAs(percentageCard) {
          start.linkTo(parent.start)
          top.linkTo(gamesColumn.top)
          bottom.linkTo(gamesColumn.bottom)
          end.linkTo(gamesColumn.start)
          width = Dimension.fillToConstraints
        }
    ) {
      StatsItem(
        statValue = winPercentage(diceRollList),
        statTitle = stringResource(id = R.string.stats_win_percentage)
      )
    }

    Column(
      modifier = Modifier
        .constrainAs(gamesColumn) {
          start.linkTo(percentageCard.end, 4.dp)
          top.linkTo(parent.top)
          end.linkTo(parent.end)
          width = Dimension.fillToConstraints
        },
      verticalArrangement = Arrangement.SpaceBetween,
    ) {
      Card(Modifier.padding(4.dp)) {
        StatsItem(
          statValue = diceRollList.filter { it.rollWin }.size.toString(),
          statTitle = stringResource(id = R.string.stats_games_won)
        )
      }
      Card(Modifier.padding(4.dp))  {
        StatsItem(
          statValue = diceRollList.size.toString(),
          statTitle = stringResource(id = R.string.stats_games_played)
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
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .height(IntrinsicSize.Max) // Specify a height for the Column
      .padding(8.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Text(
      text = statValue,
      style = MaterialTheme.typography.bodyLarge
    )
    Text(
      text = statTitle,
      style = MaterialTheme.typography.bodyLarge
    )
  }
}

@Composable
fun StatsListContent(diceRollList: List<DiceRoll>) {
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