package com.swayy.core.domain.repository

import com.swayy.core.domain.model.MatchInfo
import com.swayy.core.domain.model.WebMatch
import com.swayy.core.util.Resource

interface MatchInfoRepository {
    suspend fun getMatchInfo(url: String): Resource<List<MatchInfo>>
}