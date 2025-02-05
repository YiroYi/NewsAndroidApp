package com.anushka.newsapiclient.data.api

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsAPIServiceTest {
  private lateinit var service: NewAPIService
  private lateinit var server: MockWebServer

  @Before
  fun setUp() {
    server = MockWebServer()
    service = Retrofit.Builder()
      .baseUrl(server.url(""))
      .addConverterFactory(GsonConverterFactory.create())
      .build()
      .create(NewAPIService::class.java)
  }

  private  fun enqueueMockResponse(fileName: String) {
    val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
    val source = inputStream.source().buffer()
    val mockResponse = MockResponse()
    mockResponse.setBody(source.readString((Charsets.UTF_8)))
    server.enqueue(mockResponse)
  }

  @Test
  fun getTopHeadlines_sendRequest_receivedExpected() {
    runBlocking {
      enqueueMockResponse("newsresponse.json")
      val responseBody = service.getTopHeadlines("us", 1)
      val request = server.takeRequest()
      assertThat(responseBody).isNotNull()
      assertThat(request.path).isEqualTo("/v2/top-headlines?country=us&apiKey=aec04da8745a43968fd525110fc500c2")

    }
  }

  @Test
  fun getTopHeadlines_receivedResponse_correctPageSize() {
    runBlocking {
      enqueueMockResponse("newsresponse.json")
      val responseBody = service.getTopHeadlines("us", 1).body()
      val articlesList = responseBody!!.articles
      assertThat(articlesList.size).isEqualTo(18)
    }
  }

  @Test
  fun getTopHeadlines_receivedResponse_correctContent() {
    runBlocking {
      enqueueMockResponse("newsresponse.json")
      val responseBody = service.getTopHeadlines("us", 1).body()
      val articlesList = responseBody!!.articles
      val article = articlesList[0]
      assertThat(article.publishedAt).isEqualTo("2025-01-23T22:17:31Z")
      assertThat(article.url).isEqualTo("https://www.espn.com/soccer/report/_/gameId/720483")
    }
  }

  @After
  fun tearDown() {
    server.shutdown()
  }
}