package com.appcoins.diceroll.feature.roll_game.ui.payments.result

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import com.appcoins.diceroll.core.design.theme.DiceRollTheme
import kotlinx.coroutines.launch

@Composable
fun PaymentsResult(uiState: PaymentsResultState, onPaymentSuccess: suspend () -> Unit) {
  when (uiState) {
    is PaymentsResultState.Initialized -> {}
    is PaymentsResultState.Loading -> LoadingContent()
    is PaymentsResultState.UserCanceled -> UserCanceledContent()
    is PaymentsResultState.Failed -> FailedContent()
    is PaymentsResultState.Success -> SuccessContent(onPaymentSuccess)
  }
}

@Composable
fun LoadingContent() {
  Log.d("RESULT-UI", "PaymentsResult: LoadingContent: ")
}

@Composable
fun UserCanceledContent() {
  Log.d("RESULT-UI", "PaymentsResult: UserCanceledContent: ")
}

@Composable
fun FailedContent() {
  Log.d("RESULT-UI", "PaymentsResult: FailedContent: ")
}

@Composable
fun SuccessContent(onPaymentSuccess: suspend () -> Unit) {
  val scope = rememberCoroutineScope()
  LaunchedEffect(scope) {
    onPaymentSuccess()
  }
  Log.d("RESULT-UI", "PaymentsResult: SuccessContent: ")
}

@Preview
@Composable
fun PreviewPaymentsSuccessContent() {
  DiceRollTheme(darkTheme = true) {
    SuccessContent(onPaymentSuccess = {})
  }
}
