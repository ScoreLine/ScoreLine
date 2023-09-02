package com.swayy.core.data.repository

import com.swayy.core.data.WebData.scrapePremierLeagueStandings
import com.swayy.core.data.mapper.toStanding
import com.swayy.core.domain.model.LeagueStanding
import com.swayy.core.domain.repository.LeagueStandingRepository
import com.swayy.core.util.Resource
import com.swayy.core.util.safeApiCall
import kotlinx.coroutines.Dispatchers

class LeagueStandingRepositoryImpl(
) : LeagueStandingRepository {
    override suspend fun getLeagueStanding(league: String): Resource<List<LeagueStanding>> {
        return safeApiCall(Dispatchers.IO) {
            val response = scrapePremierLeagueStandings(league = league)
            response.map { it.toStanding() }
        }
    }
}