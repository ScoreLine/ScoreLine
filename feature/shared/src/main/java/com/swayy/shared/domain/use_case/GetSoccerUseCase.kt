package com.swayy.shared.domain.use_case

import com.swayy.core.util.Resource
import com.swayy.shared.domain.model.ClubItem
import com.swayy.shared.domain.model.Soccer
import com.swayy.shared.domain.repository.SoccerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSoccerUseCase @Inject constructor(
    private val repository: SoccerRepository
) {
    suspend operator fun invoke(matchLink: String): Flow<Resource<List<Soccer>>> {
        return repository.getSoccer(matchLink)
    }
}