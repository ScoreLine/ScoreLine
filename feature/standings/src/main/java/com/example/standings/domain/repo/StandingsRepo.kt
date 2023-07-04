package com.example.standings.domain.repo

import com.example.standings.domain.model.LeaguesDomainModel
import com.example.standings.domain.model.StandingsDomainModel
import com.example.standings.domain.model.TopAssistsDomainModel
import com.example.standings.domain.model.TopScorersDomainModel
import com.swayy.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface StandingsRepo {
    suspend fun getStandings(season: Int,league: Int): Flow<Resource<List<StandingsDomainModel>>>
    suspend fun getLeagues(): Flow<Resource<List<LeaguesDomainModel>>>
    suspend fun getTopScorers(season: Int,league: Int): Flow<Resource<List<TopScorersDomainModel>>>
    suspend fun getTopAssist(season: Int,league: Int): Flow<Resource<List<TopAssistsDomainModel>>>

}