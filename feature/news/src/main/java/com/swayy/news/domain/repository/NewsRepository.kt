package com.swayy.news.domain.repository

import com.swayy.core.util.Resource
import com.swayy.news.domain.model.NewsItem
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    suspend fun getNews(): Flow<Resource<List<NewsItem>>>

}