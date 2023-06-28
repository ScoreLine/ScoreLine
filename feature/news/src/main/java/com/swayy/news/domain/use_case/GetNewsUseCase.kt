package com.swayy.news.domain.use_case

import com.swayy.core.util.Resource
import com.swayy.news.domain.model.NewsItem
import com.swayy.news.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    suspend operator fun invoke():Flow<Resource<List<NewsItem>>>{
        return repository.getNews()
    }
}