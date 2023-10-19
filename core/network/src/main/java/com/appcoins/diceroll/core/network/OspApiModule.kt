package com.appcoins.diceroll.core.network

import com.appcoins.diceroll.core.network.annotations.HttpClient
import com.appcoins.diceroll.core.network.annotations.RetrofitClient
import com.appcoins.diceroll.core.network.api.OspApi
import com.appcoins.diceroll.core.utils.ospUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class OspApiModule {

  @Singleton
  @Provides
  @HttpClient
  fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
      .addInterceptor(HttpLoggingInterceptor())
      .build()
  }

  @Singleton
  @Provides
  @RetrofitClient
  fun provideDiceRollRetrofit(@HttpClient client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
      .baseUrl(ospUrl)
      .client(client)
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }

  @Singleton
  @Provides
  fun provideOspApi(@RetrofitClient retrofit: Retrofit): OspApi {
    return retrofit.create(OspApi::class.java)
  }
}