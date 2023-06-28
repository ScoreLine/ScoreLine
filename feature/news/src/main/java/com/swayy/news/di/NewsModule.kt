package com.swayy.news.di

import com.swayy.core_database.dao.MatchesDao
import com.swayy.core_database.dao.NewsDao
import com.swayy.core_network.LiveScoreApi
import com.swayy.news.data.NewsRepositoryImpl
import com.swayy.news.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NewsModule {

    @Provides
    @Singleton
    fun provideNewsRepository(
        newsDao: NewsDao

    ): NewsRepository {
        return NewsRepositoryImpl(
            newsDao = newsDao
        )
    }

}