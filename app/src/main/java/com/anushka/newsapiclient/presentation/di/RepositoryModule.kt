package com.anushka.newsapiclient.presentation.di

import com.anushka.newsapiclient.data.repository.NewsRepositoryImpl
import com.anushka.newsapiclient.data.repository.dataSource.NewsRemoteDataSource
import com.anushka.newsapiclient.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class RepositoryModule {

  @Singleton
  @Provides
  fun provideNewsRepository(newsRemoteDataSource: NewsRemoteDataSource): NewsRepository {
    return NewsRepositoryImpl(newsRemoteDataSource)
  }
}