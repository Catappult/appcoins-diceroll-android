package com.appcoins.diceroll.feature.payments.ui.result

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import com.appcoins.diceroll.core.ui.design.R
import com.appcoins.diceroll.core.ui.design.theme.DiceRollTheme
import com.appcoins.diceroll.core.ui.widgets.ErrorAnimation
import com.appcoins.diceroll.core.ui.widgets.LoadingAnimation
import com.appcoins.diceroll.core.ui.widgets.SuccessAnimation

@Composable
fun PaymentsResult(uiState: PaymentsResultState, onPaymentSuccess: suspend () -> Unit) {
  when (uiState) {
    is PaymentsResultState.Initialized -> {}
    is PaymentsResultState.Loading -> LoadingAnimation(titleStringResource = R.string.payments_loading)
    is PaymentsResultState.UserCanceled -> ErrorAnimation(
      titleStringResource = R.string.payments_user_canceled_title,
      bodyStringResource = R.string.payments_user_canceled_body
    )

    is PaymentsResultState.Failed -> ErrorAnimation(
      titleStringResource = R.string.payments_failed_title,
      bodyStringResource = R.string.payments_failed_body
    )

    is PaymentsResultState.Success -> SuccessContent(onPaymentSuccess)
  }
}

@Composable
fun SuccessContent(onPaymentSuccess: suspend () -> Unit) {
  LaunchedEffect(rememberCoroutineScope()) {
    onPaymentSuccess()
  }
  Column {
    SuccessAnimation(
      titleStringResource = R.string.payments_success_title,
      bodyStringResource = R.string.payments_success_body
    )
  }
}

@Preview
@Composable
fun PreviewPaymentsLoadingContent() {
  DiceRollTheme(darkTheme = true) {
    LoadingAnimation(titleStringResource = R.string.payments_loading)
  }
}

@Preview
@Composable
fun PreviewPaymentsUserCanceledContent() {
  DiceRollTheme(darkTheme = true) {
    ErrorAnimation(
      titleStringResource = R.string.payments_user_canceled_title,
      bodyStringResource = R.string.payments_user_canceled_body
    )
  }
}

@Preview
@Composable
fun PreviewPaymentsFailedContent() {
  DiceRollTheme(darkTheme = true) {
    ErrorAnimation(
      titleStringResource = R.string.payments_failed_title,
      bodyStringResource = R.string.payments_failed_body
    )
  }
}

@Preview
@Composable
fun PreviewPaymentsSuccessContent() {
  DiceRollTheme(darkTheme = true) {
    SuccessAnimation(
      titleStringResource = R.string.payments_success_title,
      bodyStringResource = R.string.payments_success_body
    )
  }
}
