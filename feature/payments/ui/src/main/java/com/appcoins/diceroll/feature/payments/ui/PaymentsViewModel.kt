package com.appcoins.diceroll.feature.payments.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appcoins.diceroll.core.navigation.destinations.DestinationArgs
import com.appcoins.diceroll.core.utils.PurchaseResultStream
import com.appcoins.diceroll.core.utils.extensions.takeUntilTimeout
import com.appcoins.diceroll.core.utils.listen
import com.appcoins.diceroll.feature.payments.ui.options.PaymentsOptionsUiState
import com.appcoins.diceroll.feature.payments.ui.result.PaymentsResultUiState
import com.appcoins.diceroll.feature.roll_game.data.DEFAULT_ATTEMPTS_NUMBER
import com.appcoins.diceroll.feature.roll_game.data.usecases.ResetAttemptsUseCase
import com.appcoins.diceroll.payments.appcoins.osp.data.model.OspCallbackState
import com.appcoins.diceroll.payments.appcoins.osp.data.usecases.PollOspCallbackUseCase
import com.appcoins.sdk.billing.ResponseCode
import com.appcoins.sdk.billing.listeners.PurchaseResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.produceIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.minutes

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

      is PaymentsIntegration.SDK -> {
        observeSdkResult()
      }
    }
  }

  private fun observeOspCallback(orderReference: Result<String>) {
    orderReference.fold(
      onSuccess = { reference ->
        pollOspCallbackUseCase(reference)
          .map { callbackResult ->
            when (callbackResult.status) {
              OspCallbackState.COMPLETED -> PaymentsResultUiState.Success
              OspCallbackState.CANCELED -> PaymentsResultUiState.UserCanceled
              OspCallbackState.FAILED -> PaymentsResultUiState.Failed
              OspCallbackState.PENDING -> PaymentsResultUiState.Loading
            }
          }
          .onEach { paymentResultUiState -> _paymentResultState.value = paymentResultUiState }
          .takeUntilTimeout(10.minutes)
          .catch {
            _paymentResultState.value = PaymentsResultUiState.Failed
          }
          .produceIn(viewModelScope)
      },
      onFailure = {
        _paymentResultState.value = PaymentsResultUiState.Failed
      }
    )
  }

  private fun observeSdkResult() {
    viewModelScope.launch {
        PurchaseResultStream.listen<PurchaseResponse>().collect {
        val paymentState = when (it.responseCode) {
            ResponseCode.OK.value -> PaymentsResultUiState.Success
            ResponseCode.USER_CANCELED.value -> PaymentsResultUiState.UserCanceled
          else -> PaymentsResultUiState.Failed
        }
        _paymentResultState.value = paymentState
      }
    }
  }

  suspend fun resetAttemptsLeft() {
    resetAttemptsUseCase()
  }
}
