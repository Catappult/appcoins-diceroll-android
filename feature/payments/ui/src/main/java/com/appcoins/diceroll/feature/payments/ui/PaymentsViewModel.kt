package com.appcoins.diceroll.feature.payments.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appcoins.diceroll.core.navigation.destinations.DestinationArgs
import com.appcoins.diceroll.feature.payments.ui.options.PaymentsOptionsUiState
import com.appcoins.diceroll.feature.payments.ui.result.PaymentsResultUiState
import com.appcoins.diceroll.feature.roll_game.data.DEFAULT_ATTEMPTS_NUMBER
import com.appcoins.diceroll.feature.roll_game.data.usecases.GetAttemptsUseCase
import com.appcoins.diceroll.feature.roll_game.data.usecases.ResetAttemptsUseCase
import com.appcoins.diceroll.payments.appcoins.osp.data.usecases.ObserveOspCallbackUseCase
import com.appcoins.diceroll.payments.appcoins.osp.data.model.OspCallbackState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PaymentsViewModel @Inject constructor(
  private val resetAttemptsUseCase: ResetAttemptsUseCase,
  private val getAttemptsUseCase: GetAttemptsUseCase,
  private val observeOspCallbackUseCase: ObserveOspCallbackUseCase,
  val savedStateHandle: SavedStateHandle,
) : ViewModel() {

  private val itemId = savedStateHandle.get<String>(DestinationArgs.ItemId)

  private val _paymentOptionsState =
    MutableStateFlow<PaymentsOptionsUiState>(PaymentsOptionsUiState.Loading)
  internal val paymentOptionsState: StateFlow<PaymentsOptionsUiState> get() = _paymentOptionsState

  init {
    getAttemptsUseCase()
      .map { attemptsLeft ->
        when {
          itemId == null -> PaymentsOptionsUiState.Error
          attemptsLeft == DEFAULT_ATTEMPTS_NUMBER -> PaymentsOptionsUiState.NotAvailable
          else -> PaymentsOptionsUiState.Available(itemId)
        }
      }
      .onEach { _paymentOptionsState.value = it }
      .launchIn(viewModelScope)
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
        // Handle PaymentsIntegration.SDK case here
      }
    }
  }

  private fun observeOspCallback(orderReference: String) {
    observeOspCallbackUseCase(orderReference = orderReference)
      .map { ospResult ->
        when (ospResult.result) {
          OspCallbackState.COMPLETED -> {
            resetAttemptsLeft()
            PaymentsResultUiState.Success
          }

          OspCallbackState.CANCELED -> PaymentsResultUiState.UserCanceled
          OspCallbackState.FAILED -> PaymentsResultUiState.Failed
          OspCallbackState.PENDING -> PaymentsResultUiState.Loading
        }
      }
      .onEach { _paymentResultState.value = it }
      .launchIn(viewModelScope)
  }

  suspend fun resetAttemptsLeft() {
    resetAttemptsUseCase()
  }
}
