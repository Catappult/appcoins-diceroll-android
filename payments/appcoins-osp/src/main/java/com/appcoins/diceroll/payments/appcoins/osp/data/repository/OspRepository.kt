package com.appcoins.diceroll.payments.appcoins.osp.data.repository

import com.appcoins.diceroll.core.network.api.OspApi
import com.appcoins.diceroll.payments.appcoins.osp.data.OspCallbackResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OspRepository @Inject constructor(
  private val ospApi: OspApi,
) {

  suspend fun getOspUrl(product: String): String {
    return withContext(Dispatchers.IO) { ospApi.getOspUrl(product = product).url }
  }

  fun observeCallbackResult(): Flow<OspCallbackResult> {
    return flow {
      ospApi.getCallbackResult().map { response ->
        emit(response.toOspCallbackResult())
      }
    }.flowOn(Dispatchers.IO)
  }
}

