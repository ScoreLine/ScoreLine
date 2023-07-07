package com.swayy.shared.domain.repository

import androidx.lifecycle.LiveData
import com.swayy.core.util.Resource
import com.swayy.shared.domain.model.Favorite
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    suspend fun insertFavorite(favorite: Favorite): Resource<Boolean>

    suspend fun getFavorites(): Resource<Flow<List<Favorite>>>

    fun getASingleFavorite(id: Int): LiveData<Favorite?>

    fun isOnlineFavorite(name: String): LiveData<Boolean>

    suspend fun deleteOneFavorite(favorite: Favorite)

    suspend fun deleteAllFavorites()

    suspend fun deleteAnOnlineFavorite(name: String)
}