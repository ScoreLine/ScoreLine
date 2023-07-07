package com.swayy.core_database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.swayy.core_database.model.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {
    @Insert
    suspend fun insertAFavorite(favoriteEntity: FavoriteEntity)

    @Query("SELECT * FROM favorites_table ORDER BY id DESC")
    fun getFavorites(): Flow<List<FavoriteEntity>>

    @Query("SELECT * FROM favorites_table WHERE id  == :id")
    fun getAFavoriteById(id: Int): LiveData<FavoriteEntity?>

    @Query("SELECT isFavorite FROM favorites_table WHERE name = :name")
    fun onlineInFavorites(name: String): LiveData<Boolean>

    @Delete
    suspend fun deleteAFavorite(favoriteEntity: FavoriteEntity)

    @Query("DELETE FROM favorites_table")
    suspend fun deleteAllFavorites()

    @Query("DELETE FROM favorites_table WHERE name = :name")
    suspend fun deleteAnOnlineFavorite(name: String)
}
