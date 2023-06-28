package com.swayy.core_database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.swayy.core_database.model.NewsEntity

@Dao
interface NewsDao {

    @Query("SELECT * FROM news_table")
    fun getAllNews(): List<NewsEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(newsEntity: List<NewsEntity>)

    @Query("DELETE FROM news_table")
    suspend fun deleteNews()
}