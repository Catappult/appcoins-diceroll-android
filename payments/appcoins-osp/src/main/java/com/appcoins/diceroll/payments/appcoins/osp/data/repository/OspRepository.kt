package com.appcoins.diceroll.payments.appcoins.osp.data.repository

import com.appcoins.diceroll.core.network.api.OspApi
import com.appcoins.diceroll.payments.appcoins.osp.data.model.OspCallbackResult
import com.appcoins.diceroll.payments.appcoins.osp.data.model.OspUrl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OspRepository @Inject constructor(
  private val ospApi: OspApi,
) {

  suspend fun getOspUrl(product: String): Result<OspUrl> {
    return runCatching {
      withContext(Dispatchers.IO) {
        ospApi.getOspUrl(product = product).toOspUrl()
      }
    }.fold(
      onSuccess = {
        Result.success(it)
      },
      onFailure = {
        Result.failure(it)
      }
    )
  }

  fun observeCallbackResult(orderReference: String): Flow<OspCallbackResult> {
    return flow {
      while (true) {
        emit(ospApi.getCallbackResult(orderReference = orderReference).toOspCallbackResult())
      }
    }.flowOn(Dispatchers.IO)
  }
}
