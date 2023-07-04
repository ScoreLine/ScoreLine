package com.example.standings.domain.repo.usecase


import com.example.standings.domain.model.TopAssistsDomainModel
import com.example.standings.domain.repo.StandingsRepo
import com.swayy.core.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AssistsUseCase @Inject constructor(
    private val standingsRepo: StandingsRepo
) {
    suspend operator fun invoke(season: Int, league: Int): Flow<Resource<List<TopAssistsDomainModel>>> {
        return standingsRepo.getTopAssist(season = season, league = league)
    }
}