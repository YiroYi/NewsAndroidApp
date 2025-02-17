package com.anushka.newsapiclient.data.repository

import com.anushka.newsapiclient.data.model.APIResponse
import com.anushka.newsapiclient.data.model.Article
import com.anushka.newsapiclient.data.repository.dataSource.NewsRemoteDataSource
import com.anushka.newsapiclient.data.util.Resource
import com.anushka.newsapiclient.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class NewsRepositoryImpl(private val newsRemoteDataSource: NewsRemoteDataSource): NewsRepository {
  override suspend fun getNewsHeadlines(country:String, page: Int): Resource<APIResponse> {
    val response = newsRemoteDataSource.getTopHeadlines(country, page)
    return responseToSource(response)
  }

  private fun responseToSource(response: Response<APIResponse>): Resource<APIResponse> {
    return if (response.isSuccessful) {
        // Handle successful response
        response.body()?.let { result ->
            // Return successful result
            Resource.Success(result)
        } ?: run {
            // Handle the case where the body is null, if applicable
            Resource.Error("No data received.")
        }
    } else {
        // Handle error response
        Resource.Error(response.message())
    }
}

  override suspend fun getSearchedNews(searchQuery: String): Resource<APIResponse> {
    TODO("Not yet implemented")
  }

  override suspend fun saveNews(article: Article) {
    TODO("Not yet implemented")
  }

  override suspend fun deleteNews(article: Article) {
    TODO("Not yet implemented")
  }

  override fun getSavedNews(): Flow<List<Article>> {
    TODO("Not yet implemented")
  }

}