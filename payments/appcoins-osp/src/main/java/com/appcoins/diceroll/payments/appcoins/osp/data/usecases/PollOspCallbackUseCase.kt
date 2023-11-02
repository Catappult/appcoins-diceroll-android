package com.appcoins.diceroll.payments.appcoins.osp.data.usecases

import com.appcoins.diceroll.core.network.HttpCodes.isClientError
import com.appcoins.diceroll.core.network.HttpCodes.isServerError
import com.appcoins.diceroll.payments.appcoins.osp.data.model.OspCallbackResult
import com.appcoins.diceroll.payments.appcoins.osp.data.model.OspCallbackState
import com.appcoins.diceroll.payments.appcoins.osp.data.repository.OspRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.transformWhile
import retrofit2.HttpException
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

class PollOspCallbackUseCase @Inject constructor(
  private val ospRepository: OspRepository
) {

  operator fun invoke(orderReference: String): Flow<OspCallbackResult> {
    return ospRepository.observeCallbackResult(orderReference = orderReference)
      .transformWhile { value ->
        emit(value)
        delay(10.seconds)
        !value.status.isEndStatus()
      }
      .retry(retries = 3) { cause ->
        delay(10.seconds)
        shouldRetry(cause)
      }
      .catch {
        emit(OspCallbackResult(OspCallbackState.FAILED))
      }
      .flowOn(Dispatchers.IO)
  }

  private fun shouldRetry(cause: Throwable): Boolean {
    if (cause is HttpException && (cause.code().isClientError() || cause.code().isServerError())) {
      return true
    }
    return false
  }

  private fun OspCallbackState.isEndStatus(): Boolean {
    return this == OspCallbackState.COMPLETED ||
        this == OspCallbackState.FAILED ||
        this == OspCallbackState.CANCELED
  }
}
