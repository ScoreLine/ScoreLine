package com.swayy.matches.domain.use_case

import com.swayy.core.util.Resource
import com.swayy.matches.domain.model.HeadToHeadDomainModel
import com.swayy.matches.domain.repository.MatchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHeadToHeadUseCase @Inject constructor(
    private val repository: MatchRepository
) {
    suspend operator fun invoke(h2h: Int): Flow<Resource<List<HeadToHeadDomainModel>>> {
        return repository.getHeadToHead(h2h = h2h)
    }
}