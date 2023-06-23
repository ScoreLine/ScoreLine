package com.swayy.matches.domain.repository

import com.swayy.core.util.Resource
import com.swayy.matches.domain.model.Lineup
import com.swayy.matches.domain.model.Match
import kotlinx.coroutines.flow.Flow

interface MatchRepository {

    fun getMatch(date: String): Flow<Resource<List<Match>>>
    fun getLineup(fixture: Int): Flow<Resource<List<Lineup>>>
}