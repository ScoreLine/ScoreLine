package com.swayy.shared.domain.use_case

import com.swayy.core.util.Resource
import com.swayy.shared.domain.model.ClubItem
import com.swayy.shared.domain.repository.ClubRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetClubsUseCase @Inject constructor(
    private val repository: ClubRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<ClubItem>>> {
        return repository.getClubs()
    }

}