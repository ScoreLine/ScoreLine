package com.swayy.matches.data.repository

import com.swayy.core.util.Resource
import com.swayy.core_database.dao.MatchesDao
import com.swayy.core_network.LiveScoreApi
import com.swayy.matches.data.mapper.toDomain
import com.swayy.matches.data.mapper.toEntity
import com.swayy.matches.data.mapper.toLineupDomain
import com.swayy.matches.data.mapper.toLineupEntity
import com.swayy.matches.domain.model.Lineup
import com.swayy.matches.domain.model.Match
import com.swayy.matches.domain.repository.MatchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class MatchRepositoryImpl(
    private val liveScoreApi: LiveScoreApi,
    private val matchesDao: MatchesDao
) : MatchRepository {
    override suspend fun getMatch(date: String): Flow<Resource<List<Match>>> = flow {

        val getMatchesFromDb = matchesDao.getMatches().map { it.toDomain() }
        emit(Resource.Loading(data = getMatchesFromDb))

        try {
            val apiResponse = liveScoreApi.getFixturesByDate(date = date)
            matchesDao.deleteMatches()
            matchesDao.insertMatches(apiResponse.response.map { it.toEntity() })
        } catch (exception: IOException) {
            emit(
                Resource.Error(
                    message = "Connection Lost",
                    data = getMatchesFromDb
                )
            )
        } catch (exception: HttpException) {
            emit(
                Resource.Error(
                    message = exception.message(),
                    data = getMatchesFromDb
                )
            )
        }
        val allMatches = matchesDao.getMatches().map { it.toDomain() }
        emit(Resource.Success(allMatches))

    }

    override suspend fun getLineup(fixture: Int): Flow<Resource<List<Lineup>>> = flow {
        val getMatchesFromDb = matchesDao.getLineup().map { it.toLineupDomain() }
        emit(Resource.Loading(data = getMatchesFromDb))

        try {
            val apiResponse = liveScoreApi.getLineup(fixture = fixture)
            matchesDao.deleteLineup()
            matchesDao.insertLineup(apiResponse.response.map { it.toLineupEntity() })
        } catch (exception: IOException) {
            emit(
                Resource.Error(
                    message = "Connection Lost",
                    data = getMatchesFromDb
                )
            )
        } catch (exception: HttpException) {
            emit(
                Resource.Error(
                    message = exception.message(),
                    data = getMatchesFromDb
                )
            )
        }
        val allMatches = matchesDao.getLineup().map { it.toLineupDomain() }
        emit(Resource.Success(allMatches))
    }

}