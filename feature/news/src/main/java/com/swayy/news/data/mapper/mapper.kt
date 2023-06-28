package com.swayy.news.data.mapper

import com.swayy.core_database.model.NewsEntity
import com.swayy.news.domain.model.NewsItem
import com.swayy.news.domain.model.NewsItemResponse

internal fun NewsItemResponse.toEntity(): NewsEntity {
    return NewsEntity(0, title, imageUrl, description, url)
}

internal fun NewsEntity.toDomain(): NewsItem {
    return NewsItem(title, imageUrl, description, url)
}
