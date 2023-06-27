package com.swayy.matches.domain.repository

import com.swayy.core.util.Resource
import com.swayy.matches.domain.model.Events
import com.swayy.matches.domain.model.Lineup
import com.swayy.matches.domain.model.Match
import kotlinx.coroutines.flow.Flow

interface MatchRepository {

   suspend fun getMatch(date: String): Flow<Resource<List<Match>>>
   suspend fun getLineup(fixture: Int): Flow<Resource<List<Lineup>>>
   suspend fun getEvents(fixture: Int): Flow<Resource<List<Events>>>
}