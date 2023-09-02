package com.swayy.core.domain.repository

import com.swayy.core.domain.model.LeagueStanding
import com.swayy.core.domain.model.WebMatch
import com.swayy.core.util.Resource

interface LeagueStandingRepository {
    suspend fun getLeagueStanding(league: String): Resource<List<LeagueStanding>>
}