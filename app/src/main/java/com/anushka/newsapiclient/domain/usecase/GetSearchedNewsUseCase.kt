package com.anushka.newsapiclient.domain.usecase

import com.anushka.newsapiclient.domain.repository.NewsRepository

class GetSearchedNewsUseCase(private val newsRepository: NewsRepository) {
}