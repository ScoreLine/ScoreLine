package com.swayy.core_network

import com.swayy.core.util.Constants.GET_FIXTURES
import com.swayy.core.util.Constants.GET_LINEUP
import com.swayy.core.util.Constants.GET_STANDINGS
import com.swayy.core_network.model.Fixtures.FixturesResponseDto
import com.swayy.core_network.model.lineup.LIneupResponseDto
import com.swayy.core_network.model.standings.StandingsDto
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

    @GET(GET_STANDINGS)
    suspend fun getStandings(
        @Query("league") league: Int,
        @Query("season") season: Int
    ): StandingsDto
}