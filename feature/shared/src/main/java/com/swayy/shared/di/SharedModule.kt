package com.swayy.shared.di

import com.swayy.core_database.dao.ClubsDao
import com.swayy.core_database.dao.FavoritesDao
import com.swayy.core_database.dao.NewsDao
import com.swayy.core_database.dao.SoccerDao
import com.swayy.shared.data.repository.ClubsRepositoryImpl
import com.swayy.shared.data.repository.FavoriteRepositoryImpl
import com.swayy.shared.data.repository.SoccerRepositoryImpl
import com.swayy.shared.domain.repository.ClubRepository
import com.swayy.shared.domain.repository.FavoriteRepository
import com.swayy.shared.domain.repository.SoccerRepository
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

    @Provides
    @Singleton
    fun provideFavoriteRepository(
        favoritesDao: FavoritesDao
    ): FavoriteRepository {
        return FavoriteRepositoryImpl(
            favoritesDao = favoritesDao
        )
    }

    @Provides
    @Singleton
    fun provideSoccerRepository(
        soccerDao: SoccerDao
    ): SoccerRepository {
        return SoccerRepositoryImpl(
            soccerDao = soccerDao
        )
    }
}