package com.anushka.newsapiclient.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.anushka.newsapiclient.data.model.Article

    @Database(
      entities = [Article::class],
      version = 1,
      exportSchema = false
    )
    @TypeConverters(Converters::class)
abstract class ArticleDatabase : RoomDatabase() {
  abstract fun getArticleDAO(): ArticleDAO
}