package com.anushka.newsapiclient.data.repository.dataSourceImpl

import com.anushka.newsapiclient.data.db.ArticleDAO
import com.anushka.newsapiclient.data.model.Article
import com.anushka.newsapiclient.data.repository.dataSource.NewsLocalDataSource

class NewsLocalDataSourceImpl(
  private val articleDAO: ArticleDAO
): NewsLocalDataSource {
  override suspend fun saveArticleToDB(article: Article) {
    articleDAO.insert(article)
  }
}