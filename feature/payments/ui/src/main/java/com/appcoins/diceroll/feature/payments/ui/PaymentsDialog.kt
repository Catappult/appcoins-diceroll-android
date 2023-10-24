package com.appcoins.diceroll.feature.payments.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.appcoins.diceroll.core.ui.design.R
import com.appcoins.diceroll.core.ui.widgets.ErrorAnimation
import com.appcoins.diceroll.core.ui.widgets.LoadingAnimation
import com.appcoins.diceroll.core.ui.widgets.components.DiceRollBottomSheet
import com.appcoins.diceroll.feature.payments.ui.navigation.itemIdArg
import com.appcoins.diceroll.feature.payments.ui.options.PaymentsOptions
import com.appcoins.diceroll.feature.payments.ui.options.PaymentsOptionsUiState
import com.appcoins.diceroll.feature.payments.ui.result.PaymentsResult

@Composable
fun PaymentsDialogRoute(
  onDismiss: () -> Unit,
  itemId: String,
  viewModel: PaymentsViewModel = hiltViewModel()
) {
  viewModel.savedStateHandle[itemIdArg] = itemId
  val paymentOptionsState by viewModel.paymentOptionsState.collectAsStateWithLifecycle()
  val paymentsResultState by viewModel.paymentResultState.collectAsStateWithLifecycle()
  DiceRollBottomSheet(onDismiss) {
    when (paymentOptionsState) {
      is PaymentsOptionsUiState.Loading -> {
        LoadingAnimation()
      }

      is PaymentsOptionsUiState.Error -> {
        ErrorAnimation(
          bodyMessage = stringResource(R.string.payments_sku_error_body)
        )
      }

      is PaymentsOptionsUiState.NotAvailable -> {
        ErrorAnimation(
          bodyMessage = stringResource(R.string.payments_attempts_error_body)
        )
      }

      is PaymentsOptionsUiState.Available -> {
        PaymentsOptions(
          itemId = (paymentOptionsState as PaymentsOptionsUiState.Available).itemId,
          onPaymentClick = viewModel::setPaymentResult,
        )
      }

      is PaymentsOptionsUiState.OptionSelected -> {
        PaymentsResult(
          paymentsResultState,
          viewModel::resetAttemptsLeft
        )
      }
    }
  }
}