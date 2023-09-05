package com.swayy.core.data.repository

import com.swayy.core.data.WebData.fetchMatchDetails
import com.swayy.core.data.mapper.toMatchInfo
import com.swayy.core.domain.model.MatchInfo
import com.swayy.core.domain.repository.MatchInfoRepository
import com.swayy.core.util.Resource
import com.swayy.core.util.safeApiCall
import kotlinx.coroutines.Dispatchers

class MatchInfoRepositoryImpl() : MatchInfoRepository {
    override suspend fun getMatchInfo(url: String): Resource<List<MatchInfo>> {
        return safeApiCall(Dispatchers.IO) {
            val response = fetchMatchDetails(matchLink = url)
            response.map { it.toMatchInfo() }
        }
    }
}