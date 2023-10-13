package com.appcoins.diceroll.feature.payments.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.appcoins.diceroll.feature.payments.ui.options.PaymentsOptions
import com.appcoins.diceroll.feature.payments.ui.result.PaymentsResult
import com.appcoins.diceroll.feature.roll_game.ui.payments.result.PaymentsResultState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentsDialogRoute(onDismiss: () -> Unit, viewModel: PaymentsViewModel = hiltViewModel()) {
  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
  ) {
    val paymentOptionsState by viewModel.paymentOptionsState.collectAsStateWithLifecycle()
    val paymentsResultState by viewModel.paymentResultState.collectAsStateWithLifecycle()

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