package com.appcoins.diceroll.core.network.modules.api

import com.appcoins.diceroll.core.network.modules.model.StoreDeeplinkResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StoreDeeplinkApi {

  @GET("deeplink/{app-package}")
  suspend fun getDeeplinkUrl(
    @Path("app-package") appPackage: String,
    @Query("store") storePackage: String?,
  ): StoreDeeplinkResponse
}