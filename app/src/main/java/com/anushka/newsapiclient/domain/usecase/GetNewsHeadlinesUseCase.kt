package com.anushka.newsapiclient.domain.usecase

import com.anushka.newsapiclient.domain.repository.NewsRepository

// newsapi apiKey: aec04da8745a43968fd525110fc500c2

class GetNewsHeadlinesUseCase(private val newsRepository: NewsRepository) {

}