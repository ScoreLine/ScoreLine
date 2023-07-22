package com.swayy.matches.domain.use_case

import com.swayy.core.util.Resource
import com.swayy.matches.domain.model.StatsDomainModel
import com.swayy.matches.domain.repository.MatchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStatsUseCase @Inject constructor(
    private val repository: MatchRepository) {

    suspend operator fun invoke(fixture: Int): Flow<Resource<List<StatsDomainModel>>> {
        return repository.getStats(fixture = fixture)
    }
}