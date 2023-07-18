package com.appcoins.wallet.sampleapp.diceroll.feature.roll_game.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.appcoins.wallet.sampleapp.diceroll.feature.stats.data.model.DiceRoll
import kotlin.random.Random

@Composable
internal fun RollGameRoute(
  viewModel: RollGameViewModel = hiltViewModel(),
) {
  RollGameScreen(
    viewModel::saveDiceRoll
  )
}

@Composable
fun RollGameScreen(
  onSaveDiceRoll: suspend (diceRoll: DiceRoll) -> Unit,
) {

  Log.d("CUSTOM_TAG", ": RollGameScreen: ")
//  TextField(
//    value =,
//    onValueChange =
//  )
  val (diceValue, setDiceValue) = remember { mutableStateOf(1) }

  Column(
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Text(
      text = "Dice Value: $diceValue",
      fontSize = 20.sp
    )

    Button(
      onClick = {
        val newValue = Random.nextInt(1, 7)
        setDiceValue(newValue)
      },
      modifier = Modifier.padding(top = 16.dp)
    ) {
      Text(text = "Roll Dice")
    }
  }
}

@Preview
@Composable
fun PreviewDiceRollScreen() {
  RollGameScreen({})
}
