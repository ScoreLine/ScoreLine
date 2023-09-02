package com.swayy.shared.domain.repository

import com.swayy.core.util.Resource
import com.swayy.shared.domain.model.ClubItem
import com.swayy.shared.domain.model.Soccer
import kotlinx.coroutines.flow.Flow

interface SoccerRepository {
    suspend fun getSoccer(matchLink: String): Flow<Resource<List<Soccer>>>
}