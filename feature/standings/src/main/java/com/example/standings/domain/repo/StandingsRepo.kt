package com.example.standings.domain.repo

import com.example.standings.domain.model.StandingsDomainModel
import com.swayy.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface StandingsRepo {
    suspend fun getStandings(season: Int,league: Int): Flow<Resource<List<StandingsDomainModel>>>
}