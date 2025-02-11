package com.anushka.newsapiclient.presentation.di

import android.app.Application
import com.anushka.newsapiclient.domain.usecase.GetNewsHeadlinesUseCase
import com.anushka.newsapiclient.presentation.viewmodel.NewsViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class FactoryModule {

  // Add comments for mobile
  @Singleton
  @Provides
  fun providesNewsViewModelFactory(
    application: Application,
    getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase
  ): NewsViewModelFactory {
    return NewsViewModelFactory(application, getNewsHeadlinesUseCase)
  }
}