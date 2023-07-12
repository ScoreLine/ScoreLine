package com.swayy.matches.domain.use_case

import com.swayy.core.util.Resource
import com.swayy.matches.domain.model.LiveMatchesDomain
import com.swayy.matches.domain.repository.MatchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLiveMatchesUseCase @Inject constructor(
    private val repository: MatchRepository
) {
    suspend operator fun invoke(live: String): Flow<Resource<List<LiveMatchesDomain>>> {
        return repository.getLiveMatches(live = live)
    }

}