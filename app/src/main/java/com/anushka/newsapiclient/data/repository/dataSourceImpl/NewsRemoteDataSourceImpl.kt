package com.anushka.newsapiclient.data.repository.dataSourceImpl

import com.anushka.newsapiclient.data.api.NewAPIService
import com.anushka.newsapiclient.data.model.APIResponse
import com.anushka.newsapiclient.data.repository.dataSource.NewsRemoteDataSource
import retrofit2.Response

class NewsRemoteDataSourceImpl(
  private val newsAPIService: NewAPIService,
): NewsRemoteDataSource {
  override suspend fun getTopHeadlines(): Response<APIResponse> {
    return newsAPIService.getTopHeadlines(country,page)
  }
}