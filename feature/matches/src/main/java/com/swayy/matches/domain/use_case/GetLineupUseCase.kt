package com.swayy.matches.domain.use_case

import com.swayy.core.util.Resource
import com.swayy.matches.domain.model.Lineup
import com.swayy.matches.domain.repository.MatchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLineupUseCase @Inject constructor(
    private val repository: MatchRepository
) {
    suspend operator fun invoke(fixture: Int):Flow<Resource<List<Lineup>>>{
        return repository.getLineup(fixture = fixture)
    }
}