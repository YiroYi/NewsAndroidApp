package com.anushka.newsapiclient.presentation.di

import com.anushka.newsapiclient.data.api.NewAPIService
import com.anushka.newsapiclient.data.repository.dataSource.NewsRemoteDataSource
import com.anushka.newsapiclient.data.repository.dataSourceImpl.NewsRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class RemoteDataModule {

  @Singleton
  @Provides
  fun provideNewsRemoteDataSource(newsAPIService: NewAPIService): NewsRemoteDataSource {
    return NewsRemoteDataSourceImpl(newsAPIService)
  }
}