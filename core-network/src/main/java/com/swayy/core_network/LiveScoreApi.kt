package com.swayy.core_network

import com.swayy.core.util.Constants.GET_FIXTURES
import com.swayy.core_network.model.Fixtures.FixturesResponseDto
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

}