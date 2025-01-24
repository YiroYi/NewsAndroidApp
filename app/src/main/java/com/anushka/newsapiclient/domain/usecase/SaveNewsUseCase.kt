package com.anushka.newsapiclient.domain.usecase

import com.anushka.newsapiclient.domain.repository.NewsRepository

class SaveNewsUseCase(private val newsRepository: NewsRepository) {
}