package com.anushka.newsapiclient.presentation.di

import android.util.Log
import com.anushka.newsapiclient.BuildConfig
import com.anushka.newsapiclient.data.api.NewsAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetModule {
  @Singleton
  @Provides
  fun provideRetrofit(): Retrofit {
    val url = Retrofit.Builder()
      .addConverterFactory(GsonConverterFactory.create())
      .baseUrl(BuildConfig.BASE_KEY)
      .build()
    Log.d("API_CALL", "Requesting: $url")
    return url
  }

  @Singleton
  @Provides
  fun providesNewsAPIService(retrofit: Retrofit): NewsAPIService {
    return retrofit.create(NewsAPIService::class.java)
  }
}