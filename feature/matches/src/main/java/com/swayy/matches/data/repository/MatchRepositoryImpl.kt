package com.swayy.matches.data.repository

import com.swayy.core.util.Resource
import com.swayy.core_network.LiveScoreApi
import com.swayy.matches.data.mapper.toDomain
import com.swayy.matches.domain.model.Match
import com.swayy.matches.domain.repository.MatchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class MatchRepositoryImpl(
    private val liveScoreApi: LiveScoreApi
) : MatchRepository {
    override fun getMatch(date: String): Flow<Resource<List<Match>>> = flow {

        emit(Resource.Loading())

        try {
        } catch (exception: IOException) {
            emit(
                Resource.Error(
                    message = "Connection Lost"
                )
            )
        } catch (exception: HttpException) {
            emit(
                Resource.Error(
                    message = exception.message()
                )
            )
        }
        val matches = liveScoreApi.getFixturesByDate(
            date = date
        ).response.map { it.toDomain() }
        emit(Resource.Success(matches))

    }

}