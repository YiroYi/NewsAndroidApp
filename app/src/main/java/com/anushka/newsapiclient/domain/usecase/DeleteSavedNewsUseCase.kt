package com.anushka.newsapiclient.domain.usecase

import com.anushka.newsapiclient.domain.repository.NewsRepository

class DeleteSavedNewsUseCase(private val newsRepository: NewsRepository) {
}