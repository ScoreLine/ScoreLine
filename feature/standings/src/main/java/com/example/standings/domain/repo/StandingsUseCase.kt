package com.example.standings.domain.repo

import com.example.standings.domain.model.LeaguesDomainModel
import com.example.standings.domain.model.StandingsDomainModel
import com.swayy.core.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StandingsUseCase @Inject constructor(
    private val standingsRepo: StandingsRepo
) {
    suspend operator fun invoke(season: Int, league: Int): Flow<Resource<List<StandingsDomainModel>>> {
        return standingsRepo.getStandings(season = season, league = league)
    }

    suspend operator fun invoke():Flow<Resource<List<LeaguesDomainModel>>>{
        return standingsRepo.getLeagues()
    }
}

