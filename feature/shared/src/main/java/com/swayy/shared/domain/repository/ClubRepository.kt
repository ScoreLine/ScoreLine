package com.swayy.shared.domain.repository

import com.swayy.core.util.Resource
import com.swayy.shared.domain.model.ClubItem
import kotlinx.coroutines.flow.Flow

interface ClubRepository {
    suspend fun getClubs(): Flow<Resource<List<ClubItem>>>
}