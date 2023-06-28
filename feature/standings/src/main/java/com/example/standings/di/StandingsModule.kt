package com.example.standings.di

import com.example.standings.data.repoimpl.StandingsRepoImpl
import com.example.standings.domain.repo.StandingsRepo
import com.swayy.core_database.dao.StandingsDao
import com.swayy.core_network.LiveScoreApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StandingsModule {

    @Provides
    @Singleton
    fun provideStandingsRepo(
        liveScoreApi: LiveScoreApi,
        standingsDao: StandingsDao
    ) : StandingsRepo {
        return StandingsRepoImpl(
            liveScoreApi = liveScoreApi,
            standingsDao = standingsDao
        )
    }
}