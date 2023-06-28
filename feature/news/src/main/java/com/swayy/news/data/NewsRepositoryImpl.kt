package com.swayy.news.data

import android.util.Log
import retrofit2.HttpException
import com.swayy.core.util.Resource
import com.swayy.core_database.dao.NewsDao
import com.swayy.news.data.mapper.toDomain
import com.swayy.news.data.mapper.toEntity
import com.swayy.news.domain.model.NewsItem
import com.swayy.news.domain.model.NewsItemResponse
import com.swayy.news.domain.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.io.IOException

class NewsRepositoryImpl(
    private val newsDao: NewsDao
) : NewsRepository {
    override suspend fun getNews(): Flow<Resource<List<NewsItem>>> = flow {
        val newsFromDb = newsDao.getAllNews()?.map { it.toDomain() }
        emit(Resource.Loading(data = newsFromDb))

        try {
            val netResponse = withContext(Dispatchers.IO) {
                fetchNewsFromWebsite()
            }
            newsDao.deleteNews()
            newsDao.insertNews(netResponse.map { it.toEntity() })
        } catch (exception: IOException) {
            emit(
                Resource.Error(
                    message = "Connection Lost",
                    data = newsFromDb
                )
            )
        } catch (exception: HttpException) {
            emit(
                Resource.Error(
                    message = exception.message(),
                    data = newsFromDb
                )
            )
        }

        val allNews = newsDao.getAllNews()?.map { it.toDomain() }
        emit(Resource.Success(allNews!!))
    }

    private fun fetchNewsFromWebsite(): List<NewsItemResponse> {
        val url = "https://www.goal.com/en-ke"
        val document: Document = Jsoup.connect(url).get()

        val articleElements: Elements = document.select("article")
        val maxNewsCount = minOf(articleElements.size, 10)

        val newsList = mutableListOf<NewsItemResponse>()
        for (i in 0 until maxNewsCount) {
            val element: Element = articleElements[i]
            val title = element.select("h3").text()
            val imageUrl = element.select("img").attr("src")
            val description = element.select("p").text()
            val newsUrl = element.select("a").attr("href")

            newsList.add(NewsItemResponse(title, imageUrl, description, newsUrl))
        }

        return newsList
    }
}