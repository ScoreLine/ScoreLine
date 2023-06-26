package com.swayy.matches.domain.use_case

import com.swayy.core.util.Resource
import com.swayy.matches.domain.model.Match
import com.swayy.matches.domain.repository.MatchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMatchUseCase @Inject constructor(
    private val repository: MatchRepository
) {
    suspend operator fun invoke(date: String): Flow<Resource<List<Match>>>{
        return repository.getMatch(date = date)
    }

}