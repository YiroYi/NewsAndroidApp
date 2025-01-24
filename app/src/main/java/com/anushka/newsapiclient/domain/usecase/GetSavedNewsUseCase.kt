package com.anushka.newsapiclient.domain.usecase

import com.anushka.newsapiclient.domain.repository.NewsRepository

class GetSavedNewsUseCase(private val newsRepository: NewsRepository) {
}