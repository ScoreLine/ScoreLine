package com.swayy.matches.data.repository

import com.swayy.core.util.Resource
import com.swayy.core_database.dao.MatchesDao
import com.swayy.core_network.LiveScoreApi
import com.swayy.matches.data.mapper.toDomain
import com.swayy.matches.data.mapper.toEntity
import com.swayy.matches.data.mapper.toEventsDomain
import com.swayy.matches.data.mapper.toEventsEntity
import com.swayy.matches.data.mapper.toHead2HeadDomain
import com.swayy.matches.data.mapper.toHeadToHeadEntity
import com.swayy.matches.data.mapper.toLineupDomain
import com.swayy.matches.data.mapper.toLineupEntity
import com.swayy.matches.data.mapper.toStatsDomain
import com.swayy.matches.data.mapper.toStatsEntity
import com.swayy.matches.domain.model.Events
import com.swayy.matches.domain.model.HeadToHeadDomainModel
import com.swayy.matches.domain.model.Lineup
import com.swayy.matches.domain.model.Match
import com.swayy.matches.domain.model.StatsDomainModel
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
            apiResponse.response.map { it.toLineupEntity() }.let { matchesDao.insertLineup(it) }
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

    override suspend fun getEvents(fixture: Int): Flow<Resource<List<Events>>> = flow {
        val getMatchesFromDb = matchesDao.getEvents().map { it.toEventsDomain() }
        emit(Resource.Loading(data = getMatchesFromDb))

        try {
            val apiResponse = liveScoreApi.getEvents(fixture = fixture)
            matchesDao.deleteEvents()
            apiResponse.response.map { it.toEventsEntity() }.let { matchesDao.insertEvents(it) }
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
        val allMatches = matchesDao.getEvents().map { it.toEventsDomain() }
        emit(Resource.Success(allMatches))
    }

    override suspend fun getStats(fixture: String): Flow<Resource<List<StatsDomainModel>>> = flow {
        val getStatsFromDb = matchesDao.getStats().map { it.toStatsDomain() }
        emit(Resource.Loading(data = getStatsFromDb))

        try {
            val apiResponse = liveScoreApi.getStats(fixture = fixture)
            matchesDao.deleteStats()
            apiResponse.response.map { it.toStatsEntity() }.let { matchesDao.insertStats(it) }
        } catch (exception: IOException) {
            emit(
                Resource.Error(
                    message = "Connection Lost",
                    data = getStatsFromDb
                )
            )
        } catch (exception: HttpException) {
            emit(
                Resource.Error(
                    message = exception.message(),
                    data = getStatsFromDb
                )
            )
        }
        val allStats = matchesDao.getStats().map { it.toStatsDomain() }
        emit(Resource.Success(allStats))
    }

    override suspend fun getHeadToHead(h2h: String): Flow<Resource<List<HeadToHeadDomainModel>>> = flow {
        val getH2HFromDb = matchesDao.getH2H().map { it.toHead2HeadDomain() }
        emit(Resource.Loading(data = getH2HFromDb))

        try {
            val apiResponse = liveScoreApi.getH2H(h2h = h2h)
            matchesDao.deleteH2H()
            apiResponse.response.map { it.toHeadToHeadEntity() }.let { matchesDao.insertH2H(it) }
        } catch (exception: IOException) {
            emit(
                Resource.Error(
                    message = "Connection Lost",
                    data = getH2HFromDb
                )
            )
        } catch (exception: HttpException) {
            emit(
                Resource.Error(
                    message = exception.message(),
                    data = getH2HFromDb
                )
            )
        }
        val allH2H = matchesDao.getH2H().map { it.toHead2HeadDomain() }
        emit(Resource.Success(allH2H))
    }

}