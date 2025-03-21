package com.anushka.newsapiclient.presentation.di

import com.anushka.newsapiclient.domain.repository.NewsRepository
import com.anushka.newsapiclient.domain.usecase.GetNewsHeadlinesUseCase
import com.anushka.newsapiclient.domain.usecase.GetSearchedNewsUseCase
import com.anushka.newsapiclient.domain.usecase.SaveNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
  @Provides
  fun providesGetNewsheadLinesUseCase(
    newsRepository: NewsRepository
  ): GetNewsHeadlinesUseCase {
    return GetNewsHeadlinesUseCase(newsRepository)
  }

  @Singleton
  @Provides
  fun providesGetSearchedNewsUseCase(
    newsRepository: NewsRepository
  ): GetSearchedNewsUseCase {
    return GetSearchedNewsUseCase(newsRepository)
  }

  @Singleton
  @Provides
  fun providesSaveNewsUseCase(
    newsRepository: NewsRepository
  ): SaveNewsUseCase {
    return SaveNewsUseCase(newsRepository)
  }


}