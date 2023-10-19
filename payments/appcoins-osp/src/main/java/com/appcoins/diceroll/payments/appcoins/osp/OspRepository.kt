package com.appcoins.diceroll.payments.appcoins.osp

import com.appcoins.diceroll.core.network.api.OspApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OspRepository @Inject constructor(
  private val ospApi: OspApi,
) {

  suspend fun getOspUrl(product: String): String {
    return withContext(Dispatchers.IO) { ospApi.getOspUrl(product = product).url }
  }
}