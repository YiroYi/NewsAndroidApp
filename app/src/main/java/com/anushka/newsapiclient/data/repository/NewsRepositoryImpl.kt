package com.anushka.newsapiclient.data.repository

import com.anushka.newsapiclient.data.model.APIResponse
import com.anushka.newsapiclient.data.model.Article
import com.anushka.newsapiclient.data.repository.dataSource.NewsLocalDataSource
import com.anushka.newsapiclient.data.repository.dataSource.NewsRemoteDataSource
import com.anushka.newsapiclient.data.util.Resource
import com.anushka.newsapiclient.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class NewsRepositoryImpl(
  private val newsRemoteDataSource: NewsRemoteDataSource,
  private val newsLocalDataSource: NewsLocalDataSource
): NewsRepository {
  override suspend fun getNewsHeadlines(country:String, page: Int): Resource<APIResponse> {
    val response = newsRemoteDataSource.getTopHeadlines(country, page)
    return responseToResource(response)
  }

  override suspend fun getSearchedNews(
    country: String,
    searchQuery: String,
    page: Int
  ): Resource<APIResponse> {
    val response = newsRemoteDataSource.getSearchedNews(country, searchQuery, page)
    return responseToResource(response)
  }

  private fun responseToResource(response:Response<APIResponse>):Resource<APIResponse>{
        if(response.isSuccessful){
            response.body()?.let {result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

  override suspend fun saveNews(article: Article) {
    newsLocalDataSource.saveArticleToDB(article)
  }

  override suspend fun deleteNews(article: Article) {
    TODO("Not yet implemented")
  }

  override fun getSavedNews(): Flow<List<Article>> {
    TODO("Not yet implemented")
  }

}