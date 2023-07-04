package com.example.standings.domain.repo.usecase

import com.example.standings.domain.model.TopScorersDomainModel
import com.example.standings.domain.repo.StandingsRepo
import com.swayy.core.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ScorersUseCase @Inject constructor(
     val standingsRepo: StandingsRepo
) {
    suspend operator fun invoke(season: Int, league: Int): Flow<Resource<List<TopScorersDomainModel>>> {
        return standingsRepo.getTopScorers(season = season, league = league)
    }
}