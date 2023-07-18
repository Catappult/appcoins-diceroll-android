package com.appcoins.wallet.sampleapp.diceroll.feature.stats.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.appcoins.wallet.sampleapp.diceroll.core.design.theme.DiceRollTheme
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
  ) {
    StatsHeaderContent(diceRollList)
    StatsListContent(diceRollList)
  }
}

@Composable
fun StatsHeaderContent(diceRollList: List<DiceRoll>) {
  Row(
    modifier = Modifier.fillMaxWidth(),
  ) {
    Card {
      StatsItem(
        statValue = diceRollList.filter { !it.rollWin }.size.toString(),
        statTitle = stringResource(id = R.string.win_percentage)
      )
    }
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(start = 8.dp),
      verticalArrangement = Arrangement.SpaceBetween,
    )
    {
      Card {
        StatsItem(
          statValue = diceRollList.filter { it.rollWin }.size.toString(),
          statTitle = stringResource(id = R.string.games_won)
        )
      }
      Card {
        StatsItem(
          statValue = diceRollList.size.toString(),
          statTitle = stringResource(id = R.string.games_played)
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
      .padding(top = 8.dp),
    verticalArrangement = Arrangement.SpaceBetween,
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
        text = roll.betNumber.toString(),
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
        DiceRoll(1, true, 1, 1),
        DiceRoll(2, false, 1, 6),
        DiceRoll(3, false, 1, 4),
      )
    )
  }
}