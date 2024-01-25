package com.appcoins.diceroll.core.network.modules.api

import com.appcoins.diceroll.core.network.modules.model.OspCallbackResultResponse
import com.appcoins.diceroll.core.network.modules.model.OspUrlResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface OspApi {

  @GET("osp_url/{product}")
  suspend fun getOspUrl(
    @Path("product") product: String,
  ): OspUrlResponse

  @GET("callback_result/{order_reference}")
  suspend fun getCallbackResult(
    @Path("order_reference") orderReference: String,
  ): OspCallbackResultResponse
}