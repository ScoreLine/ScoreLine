package com.swayy.shared.di

import com.swayy.core_database.dao.ClubsDao
import com.swayy.core_database.dao.NewsDao
import com.swayy.shared.data.repository.ClubsRepositoryImpl
import com.swayy.shared.domain.repository.ClubRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedModule {

    @Provides
    @Singleton
    fun provideNewsRepository(
        clubsDao: ClubsDao

    ): ClubRepository {
        return ClubsRepositoryImpl(
            clubsDao = clubsDao
        )
    }
}