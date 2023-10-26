package com.appcoins.diceroll.feature.payments.ui

import android.content.Intent
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appcoins.diceroll.core.navigation.destinations.DestinationArgs
import com.appcoins.diceroll.core.utils.CUSTOM_TAG
import com.appcoins.diceroll.feature.payments.ui.options.PaymentsOptionsUiState
import com.appcoins.diceroll.feature.payments.ui.result.PaymentsResultUiState
import com.appcoins.diceroll.feature.roll_game.data.DEFAULT_ATTEMPTS_NUMBER
import com.appcoins.diceroll.feature.roll_game.data.usecases.ResetAttemptsUseCase
import com.appcoins.diceroll.payments.appcoins.osp.data.model.OspCallbackState
import com.appcoins.diceroll.payments.appcoins.osp.data.usecases.PollOspCallbackUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.produceIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentsViewModel @Inject constructor(
  private val resetAttemptsUseCase: ResetAttemptsUseCase,
  private val pollOspCallbackUseCase: PollOspCallbackUseCase,
  val savedStateHandle: SavedStateHandle,
) : ViewModel() {

  private val itemId = savedStateHandle.get<String>(DestinationArgs.ItemId)
  private val attempts = savedStateHandle.get<String>(DestinationArgs.AttemptsLeft)

  private val _paymentOptionsState =
    MutableStateFlow<PaymentsOptionsUiState>(PaymentsOptionsUiState.Loading)
  internal val paymentOptionsState: StateFlow<PaymentsOptionsUiState> get() = _paymentOptionsState

  init {
    when {
      itemId == null || attempts == null -> {
        _paymentOptionsState.value = PaymentsOptionsUiState.Error
      }

      attempts == DEFAULT_ATTEMPTS_NUMBER.toString() -> {
        _paymentOptionsState.value = PaymentsOptionsUiState.NotAvailable
      }

      else -> {
        _paymentOptionsState.value = PaymentsOptionsUiState.Available(itemId)
      }
    }
  }

  private val _paymentResultState =
    MutableStateFlow<PaymentsResultUiState>(PaymentsResultUiState.Initialized)
  internal val paymentResultState: StateFlow<PaymentsResultUiState> get() = _paymentResultState


  fun setPaymentResult(paymentsIntegration: PaymentsIntegration) {
    _paymentOptionsState.value = PaymentsOptionsUiState.OptionSelected
    _paymentResultState.value = PaymentsResultUiState.Loading
    when (paymentsIntegration) {
      is PaymentsIntegration.OSP -> {
        observeOspCallback(paymentsIntegration.orderReference)
      }

      is PaymentsIntegration.SDK -> {}
    }
  }

  private fun observeOspCallback(orderReference: String) {
    pollOspCallbackUseCase(orderReference = orderReference)
      .map { ospResult ->
        when (ospResult.status) {
          OspCallbackState.COMPLETED -> {
            PaymentsResultUiState.Success
          }

          OspCallbackState.CANCELED -> PaymentsResultUiState.UserCanceled
          OspCallbackState.FAILED -> PaymentsResultUiState.Failed
          OspCallbackState.PENDING -> PaymentsResultUiState.Loading
        }
      }
      .onEach { _paymentResultState.value = it }
      .produceIn(viewModelScope)
  }

  fun onActivityResult(requestCode: Int, resultCode: Int? = null, data: Intent? = null) {
    Log.d(
      CUSTOM_TAG,
      "PaymentsViewModel: onActivityResult: requestCode: $requestCode, resultCode: $resultCode, data: $data"
    )
    viewModelScope.launch {
      when (resultCode) {
        -1 -> _paymentResultState.value = PaymentsResultUiState.Success
        0 -> _paymentResultState.value = PaymentsResultUiState.UserCanceled
        else -> _paymentResultState.value = PaymentsResultUiState.Failed
      }
    }
  }

  suspend fun resetAttemptsLeft() {
    resetAttemptsUseCase()
  }
}
