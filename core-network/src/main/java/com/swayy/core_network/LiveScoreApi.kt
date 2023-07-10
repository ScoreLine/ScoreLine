package com.swayy.core_network

import com.swayy.core.util.Constants.GET_EVENTS
import com.swayy.core.util.Constants.GET_FIXTURES
import com.swayy.core.util.Constants.GET_LEAGUES
import com.swayy.core.util.Constants.GET_LINEUP
import com.swayy.core.util.Constants.GET_STANDINGS
import com.swayy.core.util.Constants.GET_STATS
import com.swayy.core.util.Constants.GET_TOPASSISTS
import com.swayy.core.util.Constants.GET_TOPSCORERS
import com.swayy.core_network.model.Fixtures.FixturesResponseDto
import com.swayy.core_network.model.events.EventsResponseDto
import com.swayy.core_network.model.leagues.LeaguesDto
import com.swayy.core_network.model.lineup.LIneupResponseDto
import com.swayy.core_network.model.standings.StandingsDto
import com.swayy.core_network.model.assists.AssistsDto
import com.swayy.core_network.model.stats.StatsDto
import com.swayy.core_network.model.topscorers.TopScorersDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import java.time.LocalDate

interface LiveScoreApi {

    @GET(GET_FIXTURES)
    suspend fun getFixturesByDate(
        @Query("date")
        date: String
    ): FixturesResponseDto

    @GET(GET_LINEUP)
    suspend fun getLineup(
        @Query("fixture")
        fixture: Int
    ): LIneupResponseDto

    @GET(GET_EVENTS)
    suspend fun getEvents(
        @Query("fixture")
        fixture: Int
    ): EventsResponseDto

    @GET(GET_STANDINGS)
    suspend fun getStandings(
        @Query("league") league: Int,
        @Query("season") season: Int
    ): StandingsDto

    @GET(GET_LEAGUES)
    suspend fun getLeagues(): LeaguesDto

    @GET(GET_TOPSCORERS)
    suspend fun getTopscorers(
        @Query("league") league: Int,
        @Query("season") season: Int
    ): com.swayy.core_network.model.topscorers.TopScorersDto

    @GET(GET_TOPASSISTS)
    suspend fun getTopAssists(
        @Query("league") league: Int,
        @Query("season") season: Int
    ): com.swayy.core_network.model.assists.AssistsDto

    @GET(GET_STATS)
    suspend fun getStats(
        @Query("fixture") fixture: String
    ): StatsDto
}