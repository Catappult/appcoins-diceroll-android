package com.appcoins.diceroll.core.network.api

import com.appcoins.diceroll.core.network.model.OspUrlResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OspApi {

  @GET("get_url/")
  suspend fun getOspUrl(
    @Query("product") product: String,
    @Query("userId") userId: String? = null,
  ): OspUrlResponse
}