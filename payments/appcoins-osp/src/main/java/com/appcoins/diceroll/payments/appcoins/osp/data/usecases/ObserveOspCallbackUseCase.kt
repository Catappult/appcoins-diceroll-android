package com.appcoins.diceroll.payments.appcoins.osp.data.usecases

import android.util.Log
import com.appcoins.diceroll.core.utils.CUSTOM_TAG
import com.appcoins.diceroll.payments.appcoins.osp.data.model.OspCallbackResult
import com.appcoins.diceroll.payments.appcoins.osp.data.repository.OspRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.transformLatest
import retrofit2.HttpException
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class ObserveOspCallbackUseCase @Inject constructor(
  private val ospRepository: OspRepository
) {

  operator fun invoke(orderReference: String): Flow<OspCallbackResult> {
    return ospRepository.observeCallbackResult(orderReference = orderReference)
      .emitEvery(5.seconds)
      .retry { cause ->
        Log.d(CUSTOM_TAG, "ObserveOspCallbackUseCase: retry cause = $cause ")
        delay(10.seconds)
        isHttp404(cause)
      }
      .flowOn(Dispatchers.IO)
  }

  private fun isHttp404(cause: Throwable): Boolean {
    if (cause is HttpException && cause.code() == 404) {
      return true
    }
    return false
  }
}

fun <T> Flow<T>.emitEvery(interval: Duration): Flow<T> = transformLatest {
  while (true) {
    emit(it)
    delay(interval)
  }
}