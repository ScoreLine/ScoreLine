package com.swayy.shared.data.repository

import com.swayy.core.util.Resource
import com.swayy.core_database.dao.ClubsDao
import com.swayy.shared.data.mapper.toDomain
import com.swayy.shared.data.mapper.toEntity
import com.swayy.shared.domain.model.ClubItem
import com.swayy.shared.domain.model.ClubItemResponse
import com.swayy.shared.domain.repository.ClubRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import retrofit2.HttpException
import java.io.IOException

class ClubsRepositoryImpl(
    private val clubsDao: ClubsDao
) : ClubRepository {
    override suspend fun getClubs(): Flow<Resource<List<ClubItem>>> = flow {
        val clubsFromDb = clubsDao.getAllClubs()?.map { it.toDomain() }
        emit(Resource.Loading(data = clubsFromDb))

        try {
            val netResponse = scrapeClubs()

            clubsDao.deleteClubs()
            clubsDao.insertClubs(netResponse.map { it.toEntity() })
        } catch (exception: IOException) {
            emit(
                Resource.Error(
                    message = "Connection Lost",
                    data = clubsFromDb
                )
            )
        } catch (exception: HttpException) {
            emit(
                Resource.Error(
                    message = exception.message(),
                    data = clubsFromDb
                )
            )
        }

        val allCLubs = clubsDao.getAllClubs()?.map { it.toDomain() }
        emit(Resource.Success(allCLubs!!))
    }

    suspend fun scrapeClubs(): List<ClubItemResponse> = withContext(Dispatchers.IO) {
        val url = "https://www.premierleague.com/clubs"
        val clubs = mutableListOf<ClubItemResponse>()

        try {
            val doc = Jsoup.connect(url).get()
            val clubElements = doc.select("li.clubList__club")

            for (clubElement in clubElements) {
                val linkElement = clubElement.selectFirst("a.clubList__link")
                val imageElement = clubElement.selectFirst("img.badge-image")
                val nameElement = clubElement.selectFirst("span.name")

                val club = nameElement?.let {
                    imageElement?.let { it1 ->
                        linkElement?.let { it2 ->
                            ClubItemResponse(
                                it.text(),
                                it1.attr("src"),
                                it2.attr("href")
                            )
                        }
                    }
                }
                clubs.add(club!!)
            }

            clubs
        } catch (e: IOException) {
            e.printStackTrace()
            throw e
        }
    }
}