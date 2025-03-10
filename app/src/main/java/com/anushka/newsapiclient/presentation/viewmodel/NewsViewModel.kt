package com.anushka.newsapiclient.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anushka.newsapiclient.data.model.APIResponse
import com.anushka.newsapiclient.data.model.Article
import com.anushka.newsapiclient.data.util.Resource
import com.anushka.newsapiclient.domain.usecase.GetNewsHeadlinesUseCase
import com.anushka.newsapiclient.domain.usecase.GetSearchedNewsUseCase
import com.anushka.newsapiclient.domain.usecase.SaveNewsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(
  private val app: Application,
  private val getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase,
  private val getSearchedNewsUseCase: GetSearchedNewsUseCase,
  private val saveNewsUseCase: SaveNewsUseCase
) : AndroidViewModel(app) {
  val newHeadLines: MutableLiveData<Resource<APIResponse>> = MutableLiveData()

  fun getNewsHeadLines(country: String, page: Int) = viewModelScope.launch(Dispatchers.IO) {
    newHeadLines.postValue(Resource.Loading())

    try {
      if(isNetworkAvailable(app)) {

        val apiResult = getNewsHeadlinesUseCase.execute(country, page)
        newHeadLines.postValue(apiResult)
      } else {
        newHeadLines.postValue(Resource.Error("Internet is not available"))
      }
    } catch (e: Exception) {
      newHeadLines.postValue(Resource.Error(e.message.toString()))
    }
  }

  private fun isNetworkAvailable(context: Context?): Boolean {
    if (context == null) return false
    val connectivityManager =
      context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
      val capabilities =
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
      if (capabilities != null) {
        when {
          capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
            return true
          }

          capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
            return true
          }

          capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
            return true
          }
        }
      }
    } else {
      val activeNetworkInfo = connectivityManager.activeNetworkInfo
      if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
        return true
      }
    }
    return false

  }

  val searchedNews : MutableLiveData<Resource<APIResponse>> = MutableLiveData()

  fun searchNews(country: String, searchQuery: String, page: Int) = viewModelScope.launch {
    searchedNews.postValue(Resource.Loading())

    try {
      if (isNetworkAvailable(app)) {
        val response = getSearchedNewsUseCase.execute(country, searchQuery, page)
        searchedNews.postValue(response)

      } else {
        searchedNews.postValue(Resource.Error("No Internet Connection"))
      }
    } catch(e:Exception) {
        searchedNews.postValue(Resource.Error(e.message.toString()))
    }

  }

  fun saveArticle(article: Article) = viewModelScope.launch {
    saveNewsUseCase.execute(article)
  }
}