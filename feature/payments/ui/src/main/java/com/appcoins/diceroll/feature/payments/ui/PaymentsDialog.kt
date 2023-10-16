package com.appcoins.diceroll.feature.payments.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.appcoins.diceroll.core.ui.widgets.components.DiceRollBottomSheet
import com.appcoins.diceroll.feature.payments.ui.options.PaymentsOptions
import com.appcoins.diceroll.feature.payments.ui.result.PaymentsResult
import com.appcoins.diceroll.feature.payments.ui.result.PaymentsResultState

@Composable
fun PaymentsDialogRoute(onDismiss: () -> Unit, viewModel: PaymentsViewModel = hiltViewModel()) {
  val paymentOptionsState by viewModel.paymentOptionsState.collectAsStateWithLifecycle()
  val paymentsResultState by viewModel.paymentResultState.collectAsStateWithLifecycle()

  DiceRollBottomSheet(onDismissRequest = {
    onDismiss()
    viewModel.resetPaymentResult()
  }) {
    if (paymentsResultState != PaymentsResultState.Initialized) {
      PaymentsResult(
        paymentsResultState,
        viewModel::resetAttemptsLeft
      )
    } else {
      PaymentsOptions(
        paymentOptionsState,
        viewModel::setPaymentResult
      )
    }
  }
}