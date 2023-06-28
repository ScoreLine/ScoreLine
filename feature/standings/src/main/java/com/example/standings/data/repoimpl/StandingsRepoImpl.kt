package com.example.standings.data.repoimpl

import com.example.standings.data.mapper.toStandingsDomain
import com.example.standings.data.mapper.toStandingsEntity
import com.example.standings.domain.model.StandingsDomainModel
import com.example.standings.domain.repo.StandingsRepo
import com.swayy.core.util.Resource
import com.swayy.core_database.dao.StandingsDao
import com.swayy.core_network.LiveScoreApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import retrofit2.HttpException

class StandingsRepoImpl (
    private val liveScoreApi: LiveScoreApi,
    private val standingsDao: StandingsDao
) :StandingsRepo {
    override suspend fun getStandings(
        season: Int,
        league: Int
    ): Flow<Resource<List<StandingsDomainModel>>> = flow {
        val getStandingsFromDb = standingsDao.getStandings().map { it.toStandingsDomain() }
        emit(Resource.Loading(data = getStandingsFromDb))

        try {
            val apiResponse = liveScoreApi.getStandings(league = league,season = season)
            standingsDao.deleteStandings()
            standingsDao.insertStandings(apiResponse.response.map { it.league.toStandingsEntity()})
        } catch (exception: IOException) {
            emit(
                Resource.Error(
                    message = "Connection Lost",
                    data = getStandingsFromDb
                )
            )
        } catch (exception: HttpException) {
            emit(
                Resource.Error(
                    message = exception.message(),
                    data = getStandingsFromDb
                )
            )
        }
        val allStandings = standingsDao.getStandings().map { it.toStandingsDomain() }
        emit(Resource.Success(allStandings))

    }
}