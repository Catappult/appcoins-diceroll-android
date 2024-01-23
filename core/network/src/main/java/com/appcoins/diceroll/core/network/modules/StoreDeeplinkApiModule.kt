package com.appcoins.diceroll.core.network.modules

import com.appcoins.diceroll.core.network.annotations.HttpClient
import com.appcoins.diceroll.core.network.annotations.StoreDeeplinkRetrofitClient
import com.appcoins.diceroll.core.network.modules.api.StoreDeeplinkApi
import com.appcoins.diceroll.core.utils.storeDeepLinkUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class StoreDeeplinkApiModule {

  @Singleton
  @Provides
  @StoreDeeplinkRetrofitClient
  fun provideStoreDeeplinkRetrofit(@HttpClient client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
      .baseUrl(storeDeepLinkUrl)
      .client(client)
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }

  @Singleton
  @Provides
  fun provideStoreDeeplinkApi(@StoreDeeplinkRetrofitClient retrofit: Retrofit): StoreDeeplinkApi {
    return retrofit.create(StoreDeeplinkApi::class.java)
  }
}