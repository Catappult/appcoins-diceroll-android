package com.appcoins.diceroll.core.network.modules

import com.appcoins.diceroll.core.network.annotations.HttpClient
import com.appcoins.diceroll.core.network.annotations.OspRetrofitClient
import com.appcoins.diceroll.core.network.modules.api.OspApi
import com.appcoins.diceroll.core.utils.ospUrl
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
class OspApiModule {

  @Singleton
  @Provides
  @OspRetrofitClient
  fun provideDiceRollRetrofit(@HttpClient client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
      .baseUrl(ospUrl)
      .client(client)
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }

  @Singleton
  @Provides
  fun provideOspApi(@OspRetrofitClient retrofit: Retrofit): OspApi {
    return retrofit.create(OspApi::class.java)
  }
}