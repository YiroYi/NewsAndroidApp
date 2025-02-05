package com.anushka.newsapiclient.presentation.di

import com.anushka.newsapiclient.BuildConfig
import com.anushka.newsapiclient.data.api.NewAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class NetModule {
  @Singleton
  @Provides
  fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
      .addConverterFactory(GsonConverterFactory.create())
      .baseUrl(BuildConfig.BASE_URL)
      .build()
  }

  @Singleton
  @Provides
  fun providesNewsAPIService(retrofit: Retrofit): NewAPIService {
    return retrofit.create(NewAPIService::class.java)
  }
}