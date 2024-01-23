package com.appcoins.diceroll.core.network.modules.api

import com.appcoins.diceroll.core.network.modules.model.StoreDeeplinkResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface StoreDeeplinkApi {

  @GET("deeplink/{app-package}/{store-package}")
  suspend fun getDeeplinkUrl(
    @Path("app-package") appPackage: String,
    @Path("store-package") storePackage: String,
  ): StoreDeeplinkResponse
}