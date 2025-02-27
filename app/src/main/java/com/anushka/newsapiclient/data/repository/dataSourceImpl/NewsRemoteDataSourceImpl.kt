package com.anushka.newsapiclient.data.repository.dataSourceImpl

import android.util.Log
import com.anushka.newsapiclient.BuildConfig
import com.anushka.newsapiclient.data.api.NewsAPIService
import com.anushka.newsapiclient.data.model.APIResponse
import com.anushka.newsapiclient.data.repository.dataSource.NewsRemoteDataSource
import retrofit2.Response

class NewsRemoteDataSourceImpl(
    private val newsAPIService: NewsAPIService
):NewsRemoteDataSource {
    override suspend fun getTopHeadlines(country : String, page : Int): Response<APIResponse> {
        val response = newsAPIService.getTopHeadlines(country,page)
        return response
    }

    override suspend fun getSearchedNews(
        country: String,
        searchQuery: String,
        page: Int
    ): Response<APIResponse> {
        return newsAPIService.getSearchTopHeadlines(country, searchQuery, page)
    }
}