package com.appcoins.diceroll.feature.payments.ui.options

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appcoins.diceroll.core.ui.design.theme.DiceRollTheme
import com.appcoins.diceroll.core.utils.R
import com.appcoins.diceroll.feature.roll_game.ui.payments.result.PaymentsResultState
import kotlin.random.Random

@Composable
fun PaymentsOptions(
  uiState: PaymentsOptionsState,
  onResultPayment: (PaymentsResultState) -> Unit
) {
  when (uiState) {
    is PaymentsOptionsState.Loading -> {}
    is PaymentsOptionsState.Error -> {}
    is PaymentsOptionsState.Success -> {
      PaymentsOptionsContent(onResultPayment)
    }
  }
}

@Composable
fun PaymentsOptionsContent(
  onResultPayment: (PaymentsResultState) -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(bottom = 32.dp)
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Text(
      text = stringResource(id = R.string.payments_info),
      fontSize = 12.sp,
    )
    Button(onClick = { testResultCall(onResultPayment) }) {
      Text(text = stringResource(id = R.string.payments_buy_sdk_button))
    }
    Button(onClick = { }) {
      Text(text = stringResource(id = R.string.payments_buy_osp_button))
    }
  }
}

fun testResultCall(onResultPayment: (PaymentsResultState) -> Unit) {
  when (Random.nextInt(0, 3)) {
    0 -> onResultPayment(PaymentsResultState.Success)
    1 -> onResultPayment(PaymentsResultState.Failed)
    2 -> onResultPayment(PaymentsResultState.UserCanceled)
  }
}

@Preview
@Composable
fun PreviewPaymentsDialog() {
  DiceRollTheme(darkTheme = true) {
    PaymentsOptionsContent(
      onResultPayment = {}
    )
  }
}
