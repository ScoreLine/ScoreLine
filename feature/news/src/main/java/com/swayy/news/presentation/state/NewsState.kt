package com.swayy.news.presentation.state

import com.swayy.news.domain.model.NewsItem

data class NewsState(
    val isLoading: Boolean = false,
    val error: String = "",
    val news: List<NewsItem> = emptyList()
)
