package com.swayy.shared.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.swayy.core.util.Resource
import com.swayy.core_database.dao.FavoritesDao
import com.swayy.shared.data.mapper.toFavoriteDomain
import com.swayy.shared.data.mapper.toFavoriteEntity
import com.swayy.shared.domain.model.Favorite
import com.swayy.shared.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteRepositoryImpl(
    private val favoritesDao: FavoritesDao,

    ) : FavoriteRepository {
    override suspend fun insertFavorite(
        favorite: Favorite
    ): Resource<Boolean> {
        return try {
            favoritesDao.insertAFavorite(favorite.toFavoriteEntity())
            Resource.Success(data = true)
        } catch (e: Exception) {
            return Resource.Error(e.localizedMessage ?: "Unknown error occurred")
        }

    }

    override suspend fun getFavorites(): Resource<Flow<List<Favorite>>> {
        return try {
            Resource.Success(
                data = favoritesDao.getFavorites().map { favoritesEntity ->
                    favoritesEntity.map { it.toFavoriteDomain() }
                }
            )
        } catch (e: Exception) {
            return Resource.Error(e.localizedMessage ?: "Unknown error occurred")
        }
    }

    override fun getASingleFavorite(id: Int): LiveData<Favorite?> {
        return favoritesDao.getAFavoriteById(id = id).map {
            it?.toFavoriteDomain()
        }
    }

    override fun isOnlineFavorite(name: String): LiveData<Boolean> {
        return favoritesDao.onlineInFavorites(name = name)
    }

    override suspend fun deleteOneFavorite(favorite: Favorite) {
        return favoritesDao.deleteAFavorite(favorite.toFavoriteEntity())
    }

    override suspend fun deleteAllFavorites() {
        favoritesDao.deleteAllFavorites()
    }

    override suspend fun deleteAnOnlineFavorite(name: String) {
        favoritesDao.deleteAnOnlineFavorite(name = name)
    }
}