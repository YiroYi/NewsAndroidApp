package com.anushka.newsapiclient.presentation.di

import android.app.Application
import androidx.room.Room
import com.anushka.newsapiclient.data.db.ArticleDAO
import com.anushka.newsapiclient.data.db.ArticleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

  @Singleton
  @Provides
  fun provideNewsDatabase(app:Application): ArticleDatabase {
    return Room.databaseBuilder(app, ArticleDatabase::class.java, "news_db")
      .fallbackToDestructiveMigration().build()
  }

  @Singleton
  @Provides
  fun provideNewDao(articleDatabase: ArticleDatabase): ArticleDAO {
    return articleDatabase.getArticleDAO()
  }
}