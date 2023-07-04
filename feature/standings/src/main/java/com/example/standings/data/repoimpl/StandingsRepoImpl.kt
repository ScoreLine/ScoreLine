package com.example.standings.data.repoimpl

import com.example.standings.data.mapper.toLeaguesDomainModel
import com.example.standings.data.mapper.toLeaguesEntity
import com.example.standings.data.mapper.toStandingsDomain
import com.example.standings.data.mapper.toStandingsEntity
import com.example.standings.domain.model.LeaguesDomainModel
import com.example.standings.domain.model.StandingsDomainModel
import com.example.standings.domain.repo.StandingsRepo
import com.swayy.core.util.Resource
import com.swayy.core_database.dao.StandingsDao
import com.swayy.core_network.LiveScoreApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import retrofit2.HttpException
import android.util.Log
import com.example.standings.data.mapper.toTopAssistsDomain
import com.example.standings.data.mapper.toTopAssistsEntity
import com.example.standings.data.mapper.toTopScorersDomain
import com.example.standings.data.mapper.toTopScorersEntity
import com.example.standings.domain.model.TopAssistsDomainModel
import com.example.standings.domain.model.TopScorersDomainModel


class StandingsRepoImpl(
    private val liveScoreApi: LiveScoreApi,
    private val standingsDao: StandingsDao
) : StandingsRepo {
    override suspend fun getStandings(
        season: Int,
        league: Int
    ): Flow<Resource<List<StandingsDomainModel>>> = flow {
        val getStandingsFromDb = standingsDao.getStandings().map { it.toStandingsDomain() }
        emit(Resource.Loading(data = getStandingsFromDb))

        try {
            val apiResponse = liveScoreApi.getStandings(league = league, season = season)
            standingsDao.deleteStandings()
            standingsDao.insertStandings(apiResponse.response.map { it.league.toStandingsEntity() })
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

    override suspend fun getLeagues(): Flow<Resource<List<LeaguesDomainModel>>> = flow {
        val getLeaguesFromDb = standingsDao.getLeagues().map { it.toLeaguesDomainModel() }
        emit(Resource.Loading(data = getLeaguesFromDb))

        try {
            // Log loading from the database
            Log.d("Repository", "Loading leagues from the database: $getLeaguesFromDb")

            val apiResponse = liveScoreApi.getLeagues()

            // Log the API response
            Log.d("Repository", "API response: $apiResponse")

            standingsDao.deleteLeagues()
            standingsDao.insertLeagues(apiResponse.response.map { it.toLeaguesEntity() })
        } catch (exception: IOException) {
            // Log the connection lost error
            Log.e("Repository", "Connection lost: ${exception.message}")
            emit(Resource.Error(message = "Connection Lost", data = getLeaguesFromDb))
        } catch (exception: HttpException) {
            // Log the HTTP exception error
            Log.e("Repository", "HTTP Exception: ${exception.message()}")
            emit(Resource.Error(message = exception.message(), data = getLeaguesFromDb))
        }

        val allLeagues = standingsDao.getLeagues().map { it.toLeaguesDomainModel() }

        // Log the success with all leagues
        Log.d("Repository", "All leagues: $allLeagues")
        emit(Resource.Success(allLeagues))
    }

    override suspend fun getTopScorers(
        season: Int,
        league: Int
    ): Flow<Resource<List<TopScorersDomainModel>>> = flow {
        val getTopScorersFromDb = standingsDao.getTopScorers().map { it.toTopScorersDomain() }
        emit(Resource.Loading(data = getTopScorersFromDb))

        try {
            // Log loading from the database
            Log.d("Repository", "Loading scorers from the database: $getTopScorersFromDb")

            val apiResponse = liveScoreApi.getTopscorers(league = league, season = season)

            // Log the API response
            Log.d("Repository", "API response: $apiResponse")

            standingsDao.deleteTopScorers()
            standingsDao.insertTopScorers(apiResponse.response.map { it.toTopScorersEntity() })
        } catch (exception: IOException) {
            // Log the connection lost error
            Log.e("Repository", "Connection lost: ${exception.message}")
            emit(Resource.Error(message = "Connection Lost", data = getTopScorersFromDb))
        } catch (exception: HttpException) {
            // Log the HTTP exception error
            Log.e("Repository", "HTTP Exception: ${exception.message()}")
            emit(Resource.Error(message = exception.message(), data = getTopScorersFromDb))
        }

        val allTopScorers = standingsDao.getTopScorers().map { it.toTopScorersDomain() }

        // Log the success with all leagues
        Log.d("Repository", "All leagues: $allTopScorers")
        emit(Resource.Success(allTopScorers))
    }

    override suspend fun getTopAssist(
        season: Int,
        league: Int
    ): Flow<Resource<List<TopAssistsDomainModel>>> = flow {
        val getTopAssistsFromDb = standingsDao.getTopAssists().map { it.toTopAssistsDomain() }
        emit(Resource.Loading(data = getTopAssistsFromDb))

        try {
            val apiResponse = liveScoreApi.getTopAssists(league = league, season = season)
            standingsDao.deleteTopAssists()
            standingsDao.insertTopAssists(apiResponse.response.map { it.toTopAssistsEntity() })
        } catch (exception: IOException) {
            emit(
                Resource.Error(
                    message = "Connection Lost",
                    data = getTopAssistsFromDb
                )
            )
        } catch (exception: HttpException) {
            emit(
                Resource.Error(
                    message = exception.message(),
                    data = getTopAssistsFromDb
                )
            )
        }
        val allTopAssits = standingsDao.getTopAssists().map { it.toTopAssistsDomain() }
        emit(Resource.Success(allTopAssits))
    }


}