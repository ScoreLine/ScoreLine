package com.swayy.shared.data.repository

import com.swayy.core.util.Resource
import com.swayy.core_database.dao.SoccerDao
import com.swayy.shared.data.mapper.toDomain
import com.swayy.shared.data.mapper.toEntity
import com.swayy.shared.domain.model.ClubItemResponse
import com.swayy.shared.domain.model.Soccer
import com.swayy.shared.domain.model.SoccerItemResponse
import com.swayy.shared.domain.repository.SoccerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import retrofit2.HttpException
import java.io.IOException

class SoccerRepositoryImpl(
    private val soccerDao: SoccerDao
) : SoccerRepository {
    override suspend fun getSoccer(matchLink: String): Flow<Resource<List<Soccer>>> = flow {
        val soccerFromDb = soccerDao.getAllSoccer()?.map { it.toDomain() }
        emit(Resource.Loading(data = soccerFromDb))

        try {
            val netResponse = scrapeSoccer(matchLink = matchLink)

            soccerDao.deleteSoccer()
            soccerDao.insertSoccer(netResponse.map { it.toEntity() })
        } catch (exception: IOException) {
            emit(
                Resource.Error(
                    message = "Connection Lost",
                    data = soccerFromDb
                )
            )
        } catch (exception: HttpException) {
            emit(
                Resource.Error(
                    message = exception.message(),
                    data = soccerFromDb
                )
            )
        }

        val allSoccer = soccerDao.getAllSoccer()?.map { it.toDomain() }
        emit(Resource.Success(allSoccer!!))
    }

    suspend fun scrapeSoccer(matchLink: String): List<SoccerItemResponse> =
        withContext(Dispatchers.IO) {
            val soccerL = mutableListOf<SoccerItemResponse>()

            try {
                val doc = Jsoup.connect(matchLink).get()

                val leagueItems = doc.select(".item-list li a.item-box")

                for (item in leagueItems) {
                    val leagueName = item.select(".main-text").text()
                    val games = item.select(".sub-text1 ").text()
                    val teams = item.select(".sub-text2").text()
                    val logo = item.select(".image-main img").attr("src")
                    val flag = item.select(".flag-round").attr("src")
                    val link = item.attr("href")

                    val soccer = SoccerItemResponse(leagueName, games, teams, logo, flag, link)
                    soccerL.add(soccer)
                }

                soccerL
            } catch (e: IOException) {
                e.printStackTrace()
                throw e
            }
        }
}