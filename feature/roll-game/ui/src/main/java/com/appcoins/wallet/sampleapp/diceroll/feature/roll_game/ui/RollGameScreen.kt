package com.appcoins.wallet.sampleapp.diceroll.feature.roll_game.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberImagePainter
import com.appcoins.wallet.sampleapp.diceroll.core.utils.R
import com.appcoins.wallet.sampleapp.diceroll.feature.stats.data.model.DiceRoll
import kotlinx.coroutines.runBlocking
import kotlin.random.Random
import com.appcoins.wallet.sampleapp.diceroll.feature.roll_game.ui.R as GameR

@Composable
internal fun RollGameRoute(
  viewModel: RollGameViewModel = hiltViewModel(),
) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()
  RollGameScreen(
    uiState,
    viewModel::saveDiceRoll
  )
}

@Composable
fun RollGameScreen(
  uiState: RollGameUiState,
  onSaveDiceRoll: suspend (diceRoll: DiceRoll) -> Unit,
) {
  when (uiState) {
    RollGameUiState.Loading -> {}
    is RollGameUiState.Success -> {
      RollGameContent(
        attemptsLeft = uiState.attemptsLeft ?: DEFAULT_ATTEMPTS_NUMBER,
        onSaveDiceRoll = onSaveDiceRoll
      )
    }
  }
}

@Composable
fun RollGameContent(attemptsLeft: Int, onSaveDiceRoll: suspend (diceRoll: DiceRoll) -> Unit) {
  var diceValue by rememberSaveable { mutableStateOf(1) }
  var resultText by rememberSaveable { mutableStateOf("") }
  var attempts by rememberSaveable { mutableStateOf(attemptsLeft) }
  var betNumber by rememberSaveable { mutableStateOf("") }
  Column(
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.SpaceEvenly
  ) {
    GameDice(diceValue, resultText)
    Column(
      Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
      Row() {
        Card(
          modifier = Modifier
            .padding(8.dp)
            .weight(2f),
        ) {
          TextField(
            value = betNumber,
            onValueChange = { newValue ->
              // Check if the newValue can be converted to an integer
              betNumber = if (newValue.toIntOrNull() != null) {
                newValue
              } else {
                ""
              }
            },
            visualTransformation = DigitVisualTransformation(),
            label = { Text(stringResource(id = R.string.roll_game_guess_prompt)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
          )
        }
        Button(
          onClick = {
            if (attempts > 0 && betNumber.isNotEmpty()) {
              val bet = betNumber
              diceValue = Random.nextInt(1, 7)
              if (bet.toInt() == diceValue) {
                resultText = "Correct!"
                attempts = DEFAULT_ATTEMPTS_NUMBER // Reset attempts to the default value
              } else {
                resultText = "Incorrect!"
                attempts--
              }
            }
            runBlocking {
              onSaveDiceRoll(
                DiceRoll(
                  id = null,
                  rollWin = diceValue == betNumber.toInt(),
                  guessNumber = betNumber.toInt(),
                  resultNumber = diceValue,
                  attemptsLeft = attempts
                )
              )
            }
            betNumber = ""
          },
          Modifier
            .weight(1f)
            .align(Alignment.CenterVertically),
          enabled = attempts > 0 && betNumber.isNotEmpty()
        ) {
          Text(text = stringResource(id = R.string.roll_game_button))
        }
      }
      Text(
        text = stringResource(id = R.string.roll_game_attempts_left) + " $attempts",
        fontSize = 12.sp,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        textAlign = TextAlign.Center
      )
    }

    Button(onClick = { /*TODO*/ }) {
      Text(text = stringResource(id = R.string.roll_game_buy_button))
    }
  }
}

@Composable
private fun GameDice(diceValue: Int, resultText: String) {
  val diceImages = listOf(
    GameR.drawable.dice_six_faces_one,
    GameR.drawable.dice_six_faces_two,
    GameR.drawable.dice_six_faces_three,
    GameR.drawable.dice_six_faces_four,
    GameR.drawable.dice_six_faces_five,
    GameR.drawable.dice_six_faces_six,
  )
  Box(
    modifier = Modifier.size(200.dp),
    contentAlignment = Alignment.Center
  ) {
    Image(
      painter = rememberImagePainter(diceImages[diceValue - 1]),
      contentDescription = "Dice Image",
      modifier = Modifier.matchParentSize()
    )
    if (resultText.isNotEmpty()) {
      Text(
        text = resultText,
        fontSize = 18.sp,
        color = MaterialTheme.colorScheme.onPrimary
      )
    }
  }
}

private class DigitVisualTransformation : VisualTransformation {
  override fun filter(text: AnnotatedString): TransformedText {
    val trimmed = text.text.take(1)
    val newText = buildAnnotatedString {
      append(trimmed)
    }
    return TransformedText(newText, OffsetMapping.Identity)
  }
}

@Preview
@Composable
fun PreviewDiceRollScreen() {
  RollGameContent(
    attemptsLeft = 3,
    onSaveDiceRoll = {}
  )
}

const val DEFAULT_ATTEMPTS_NUMBER = 10
