package com.anushka.newsapiclient.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.anushka.newsapiclient.data.model.Article
import com.anushka.newsapiclient.databinding.NewsListItemBinding

class NewsAdapter {
  inner class newsViewHolder(val binding: NewsListItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bindArticle(article: Article) {
      binding.tvTitle.text = article.title
      binding.tvDescription.text = article.description
      binding.tvPublishedAt.text = article.publishedAt
      binding.tvSource.text = article.source
    }
  }
}